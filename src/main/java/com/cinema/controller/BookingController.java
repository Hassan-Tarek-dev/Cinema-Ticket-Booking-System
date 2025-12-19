package com.cinema.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.cinema.dto.SeatDTO;
import com.cinema.entity.Booking;
import com.cinema.entity.Movie;
import com.cinema.entity.Showtime;
import com.cinema.entity.User;
import com.cinema.repository.UserRepository;
import com.cinema.service.BookingFacade;
import com.cinema.service.MovieService;
import com.cinema.service.ShowtimeService;

// FACADE PATTERN
// SINGLETON PATTERN
@Controller
@RequestMapping("/booking")
public class BookingController {

    private static final Logger log = LoggerFactory.getLogger(BookingController.class);

    @Autowired
    private BookingFacade bookingFacade;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ShowtimeService showtimeService;

    @Autowired
    private MovieService movieService;

    @GetMapping("/seats/{showtimeId}")
    public String selectSeats(
            @PathVariable Long showtimeId,
            @RequestParam(required = false) Long movieId,
            Model model) {
        log.info("Displaying seat selection for showtime: {}", showtimeId);
        
        List<SeatDTO> seats = bookingFacade.getSeatLayoutForShowtime(showtimeId);
        var showtimeOpt = showtimeService.getShowtimeById(showtimeId);
        
        if (showtimeOpt.isPresent()) {
            Showtime showtime = showtimeOpt.get();
            model.addAttribute("showtime", showtime);
            model.addAttribute("movie", showtime.getMovie());
            model.addAttribute("ticketPrice", showtime.getTicketPrice());
        }

        model.addAttribute("showtimeId", showtimeId);
        model.addAttribute("movieId", movieId);
        model.addAttribute("seats", seats);
        return "booking/seat-selection";
    }

   @GetMapping("/payment")
    public String showPayment(
            @RequestParam Long movieId,
            @RequestParam Long showtimeId,
            @RequestParam List<Long> seatIds,
            Model model) {
        
        log.info("Showing payment page for showtime: {}", showtimeId);

        var movieOpt = movieService.getMovieById(movieId);
        var showtimeOpt = showtimeService.getShowtimeById(showtimeId);

        if (movieOpt.isPresent() && showtimeOpt.isPresent()) {
            Movie movie = movieOpt.get();
            Showtime showtime = showtimeOpt.get();
            
            double totalPrice = showtime.getTicketPrice() * seatIds.size();

            model.addAttribute("movieTitle", movie.getTitle());
            model.addAttribute("showtime", showtime.getShowTime());
            model.addAttribute("totalPrice", totalPrice);
            model.addAttribute("seatCount", seatIds.size());
        } else {
            return "redirect:/movies";
        }
        
        model.addAttribute("movieId", movieId);
        model.addAttribute("showtimeId", showtimeId);
        model.addAttribute("seatIds", seatIds);
        
        return "booking/payment";
    }

    // STRATEGY PATTERN
    @PostMapping("/initiate")
    public String initiateBooking(
            @RequestParam Long movieId,
            @RequestParam Long showtimeId,
            @RequestParam List<Long> seatIds,
            @RequestParam Booking.PaymentMethod paymentMethod,
            Authentication authentication,
            Model model) {
        
        log.info("Initiating booking for user: {}", authentication.getName());
        
        User user = userRepository.findByUsername(authentication.getName())
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        var booking = bookingFacade.initiateBooking(user.getId(), movieId, showtimeId, seatIds, paymentMethod);
        
        if (booking != null) {
            String txnId = "TXN-ID@" + System.currentTimeMillis();
            boolean success = bookingFacade.confirmBookingPayment(booking.getId(), txnId);
            
            if (success) {
                model.addAttribute("booking", bookingFacade.getBookingDetails(booking.getId()));
                return "booking/success";
            }
        }
        
        model.addAttribute("error", "Booking failed. Please try again.");
        return "booking/error";
    }

    @PostMapping("/confirm")
    public String confirmBooking(
            @RequestParam Long bookingId,
            @RequestParam String transactionDetails,
            Model model) {
        
        if (bookingFacade.confirmBookingPayment(bookingId, transactionDetails)) {
            model.addAttribute("booking", bookingFacade.getBookingDetails(bookingId));
            return "booking/success";
        }
        return "booking/error";
    }

    @PostMapping("/cancel/{bookingId}")
    public String cancelBooking(@PathVariable Long bookingId, Model model) {
        if (bookingFacade.cancelBooking(bookingId)) {
            model.addAttribute("message", "Booking cancelled successfully");
            return "booking/cancelled";
        }
        return "booking/error";
    }
}