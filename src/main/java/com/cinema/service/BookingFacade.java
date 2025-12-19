package com.cinema.service;

import com.cinema.entity.*;
import com.cinema.dto.*;
import com.cinema.repository.*;
import com.cinema.observer.BookingNotificationManager;
import com.cinema.observer.EmailNotificationObserver;
import com.cinema.observer.SmsNotificationObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

// FACADE PATTERN
@Service
public class BookingFacade {

    private static final Logger log = LoggerFactory.getLogger(BookingFacade.class);

    @Autowired
    private MovieService movieService;

    @Autowired
    private ShowtimeService showtimeService;

    @Autowired
    private SeatService seatService;

    @Autowired
    private BookingService bookingService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private ShowtimeRepository showtimeRepository;

    @Autowired
    private SeatRepository seatRepository;

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private BookingNotificationManager notificationManager;

    public void initializeObservers() {
        log.info("Initializing notification observers");
        notificationManager.registerObserver(new EmailNotificationObserver());
        notificationManager.registerObserver(new SmsNotificationObserver());
    }

    public List<MovieDTO> getAllMovies() {
        log.info("Facade: Getting all movies");
        List<Movie> movies = movieService.getAllActiveMovies();
        return movies.stream().map(movieService::convertToDTO).toList();
    }

    public List<MovieDTO> getMoviesByGenre(String genre) {
        log.info("Facade: Getting movies by genre: {}", genre);
        List<Movie> movies = movieService.getMoviesByGenre(genre);
        return movies.stream().map(movieService::convertToDTO).toList();
    }

    public MovieDTO getMovieWithShowtimes(Long movieId) {
        log.info("Facade: Getting movie {} with showtimes", movieId);
        Optional<Movie> movie = movieRepository.findById(movieId);
        if (movie.isEmpty()) {
            log.warn("Movie not found with ID: {}", movieId);
            return null;
        }
        return movieService.convertToDTO(movie.get());
    }

    public List<ShowtimeDTO> getShowtimesForMovie(Long movieId) {
        log.info("Facade: Getting showtimes for movie: {}", movieId);
        List<Showtime> showtimes = showtimeService.getShowtimesByMovieId(movieId);
        return showtimes.stream().map(showtimeService::convertToDTO).toList();
    }

    public List<SeatDTO> getAvailableSeatsForShowtime(Long showtimeId) {
        log.info("Facade: Getting available seats for showtime: {}", showtimeId);
        List<Seat> seats = seatService.getAvailableSeats(showtimeId);
        return seats.stream().map(seatService::convertToDTO).toList();
    }

    public List<SeatDTO> getSeatLayoutForShowtime(Long showtimeId) {
        log.info("Facade: Getting seat layout for showtime: {}", showtimeId);
        List<Seat> seats = seatService.getSeatsByShowtimeId(showtimeId);
        return seats.stream().map(seatService::convertToDTO).toList();
    }

    @Transactional
    public BookingDTO initiateBooking(Long userId, Long movieId, Long showtimeId, 
                                     List<Long> seatIds, Booking.PaymentMethod paymentMethod) {
        log.info("Facade: Initiating booking for user: {}, movie: {}, showtime: {}", 
                 userId, movieId, showtimeId);

        try {
            Optional<User> user = userRepository.findById(userId);
            if (user.isEmpty()) {
                log.error("User not found: {}", userId);
                return null;
            }

            Optional<Movie> movie = movieRepository.findById(movieId);
            Optional<Showtime> showtime = showtimeRepository.findById(showtimeId);

            if (movie.isEmpty() || showtime.isEmpty()) {
                log.error("Movie or showtime not found");
                return null;
            }

            List<Seat> selectedSeats = new ArrayList<>();
            Double totalPrice = 0.0;

            for (Long seatId : seatIds) {
                Optional<Seat> seat = seatRepository.findById(seatId);
                if (seat.isEmpty() || !seat.get().isAvailable()) {
                    log.error("Seat not available: {}", seatId);
                    return null;
                }
                
                seatService.reserveSeat(seatId);
                selectedSeats.add(seat.get());
                totalPrice += showtime.get().getTicketPrice();
            }

            // BUILDER PATTERN
            Booking booking = new BookingBuilder()
                    .withUser(user.get())
                    .withMovie(movie.get())
                    .withShowtime(showtime.get())
                    .withSeats(selectedSeats)
                    .withTotalPrice(totalPrice)
                    .withPaymentMethod(paymentMethod)
                    .build();

            Booking savedBooking = bookingService.createBooking(booking);
            log.info("Booking initiated successfully with ID: {}", savedBooking.getId());

            return bookingService.convertToDTO(savedBooking);

        } catch (Exception e) {
            log.error("Error during booking initiation", e);
            return null;
        }
    }

    @Transactional
    public boolean confirmBookingPayment(Long bookingId, String transactionDetails) {
        log.info("Facade: Confirming payment for booking: {}", bookingId);
        return bookingService.processPaymentAndConfirmBooking(bookingId, transactionDetails);
    }

    @Transactional
    public boolean cancelBooking(Long bookingId) {
        log.info("Facade: Cancelling booking: {}", bookingId);
        return bookingService.cancelBooking(bookingId);
    }

    public List<BookingDTO> getUserBookingHistory(Long userId) {
        log.info("Facade: Getting booking history for user: {}", userId);
        List<Booking> bookings = bookingService.getBookingsByUserId(userId);
        return bookings.stream().map(bookingService::convertToDTO).toList();
    }

    public BookingDTO getBookingDetails(Long bookingId) {
        log.info("Facade: Getting booking details for ID: {}", bookingId);
        Optional<Booking> booking = bookingService.getBookingById(bookingId);
        return booking.map(bookingService::convertToDTO).orElse(null);
    }

    public List<ShowtimeDTO> getUpcomingShowtimes() {
        log.info("Facade: Getting upcoming showtimes");
        List<Showtime> showtimes = showtimeService.getUpcomingShowtimes();
        return showtimes.stream().map(showtimeService::convertToDTO).toList();
    }
}
