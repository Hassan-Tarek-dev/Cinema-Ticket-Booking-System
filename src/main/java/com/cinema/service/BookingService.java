package com.cinema.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cinema.dto.BookingDTO;
import com.cinema.entity.Booking;
import com.cinema.entity.Seat;
import com.cinema.observer.BookingNotificationManager;
import com.cinema.payment.PaymentFactory;
import com.cinema.payment.PaymentProcessor;
import com.cinema.payment.PaymentStrategy;
import com.cinema.repository.BookingRepository;
import com.cinema.repository.SeatRepository;

// SINGLETON PATTERN
@Service
public class BookingService {

    private static final Logger log = LoggerFactory.getLogger(BookingService.class);

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private SeatRepository seatRepository;

    @Autowired
    private ShowtimeService showtimeService;

    @Autowired
    private SeatService seatService;

    @Autowired
    private BookingNotificationManager notificationManager;

    @Autowired
    private PaymentFactory paymentFactory;

    @Autowired
    private MovieService movieService;

    @Transactional
    public Booking createBooking(Booking booking) {
        log.info("Creating booking for user ID: {}", booking.getUser().getId());
        booking.setBookingDate(LocalDateTime.now());
        booking.setStatus(Booking.BookingStatus.PENDING);
        return bookingRepository.save(booking);
    }

    public Optional<Booking> getBookingById(Long id) {
        log.info("Fetching booking with ID: {}", id);
        return bookingRepository.findById(id);
    }

    public List<Booking> getBookingsByUserId(Long userId) {
        log.info("Fetching bookings for user ID: {}", userId);
        return bookingRepository.findByUserId(userId);
    }

    // STRATEGY PATTERN
    @Transactional
    public boolean processPaymentAndConfirmBooking(Long bookingId, String transactionDetails) {
        log.info("Processing payment for booking ID: {}", bookingId);
        
        Optional<Booking> booking = bookingRepository.findById(bookingId);
        if (booking.isEmpty()) {
            log.error("Booking not found with ID: {}", bookingId);
            return false;
        }

        Booking b = booking.get();
        
        // FACTORY PATTERN
        PaymentStrategy strategy = paymentFactory.createPaymentStrategy(b.getPaymentMethod());
        PaymentProcessor processor = new PaymentProcessor(strategy);
        
        if (processor.pay(b.getTotalPrice(), transactionDetails)) {
            b.setStatus(Booking.BookingStatus.CONFIRMED);
            b.setPaymentDate(LocalDateTime.now());
            b.setTransactionId(UUID.randomUUID().toString());
            bookingRepository.save(b);
            
            for (Seat seat : b.getSeats()) {
                seatService.confirmSeat(seat.getId());
            }
            
            // OBSERVER PATTERN
            notificationManager.notifyBookingConfirmed(b);
            
            log.info("Booking confirmed successfully with transaction ID: {}", b.getTransactionId());
            return true;
        }
        
        log.error("Payment processing failed for booking ID: {}", bookingId);
        return false;
    }

    @Transactional
    public boolean cancelBooking(Long bookingId) {
        log.info("Cancelling booking ID: {}", bookingId);
        
        Optional<Booking> booking = bookingRepository.findById(bookingId);
        if (booking.isEmpty()) {
            log.error("Booking not found with ID: {}", bookingId);
            return false;
        }

        Booking b = booking.get();
        
        if (b.getStatus() == Booking.BookingStatus.CANCELLED) {
            log.warn("Booking already cancelled: {}", bookingId);
            return false;
        }

        for (Seat seat : b.getSeats()) {
            seatService.releaseSeat(seat.getId());
        }

        b.setStatus(Booking.BookingStatus.CANCELLED);
        bookingRepository.save(b);
        
        // OBSERVER PATTERN
        notificationManager.notifyBookingCancelled(b);
        
        log.info("Booking cancelled successfully");
        return true;
    }

    public BookingDTO convertToDTO(Booking booking) {
        BookingDTO dto = new BookingDTO();
        dto.setId(booking.getId());
        dto.setUserId(booking.getUser().getId());
        dto.setMovieId(booking.getMovie().getId());
        dto.setShowtimeId(booking.getShowtime().getId());
        dto.setSeatIds(booking.getSeats().stream().map(Seat::getId).toList());
        dto.setTotalPrice(booking.getTotalPrice());
        dto.setStatus(booking.getStatus().name());
        dto.setPaymentMethod(booking.getPaymentMethod().name());
        dto.setBookingDate(booking.getBookingDate());
        dto.setPaymentDate(booking.getPaymentDate());
        dto.setTransactionId(booking.getTransactionId());
        dto.setMovie(movieService.convertToDTO(booking.getMovie()));
        dto.setShowtime(showtimeService.convertToDTO(booking.getShowtime()));
        return dto;
    }
}
