package com.cinema.observer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.cinema.entity.Booking;

// OBSERVER PATTERN
@Component
public class EmailNotificationObserver implements BookingObserver {

    private static final Logger log = LoggerFactory.getLogger(EmailNotificationObserver.class);

    @Override
    public void onBookingConfirmed(Booking booking) {
        log.info("Sending confirmation email to: {}", booking.getUser().getEmail());
        
        String subject = "Booking Confirmed - " + booking.getMovie().getTitle();
        String body = buildConfirmationEmail(booking);
        
        sendEmail(booking.getUser().getEmail(), subject, body);
        log.info("Confirmation email sent successfully");
    }

    @Override
    public void onBookingCancelled(Booking booking) {
        log.info("Sending cancellation email to: {}", booking.getUser().getEmail());
        
        String subject = "Booking Cancelled - " + booking.getMovie().getTitle();
        String body = buildCancellationEmail(booking);
        
        sendEmail(booking.getUser().getEmail(), subject, body);
        log.info("Cancellation email sent successfully");
    }

    private String buildConfirmationEmail(Booking booking) {
        return String.format(
            "Dear %s,\n\n" +
            "Your booking for %s has been confirmed!\n" +
            "Showtime: %s\n" +
            "Total Price: Rs. %.2f\n" +
            "Seats: %s\n\n" +
            "Thank you for booking with us!",
            booking.getUser().getFullName(),
            booking.getMovie().getTitle(),
            booking.getShowtime().getShowTime(),
            booking.getTotalPrice(),
            booking.getSeats().stream().map(s -> s.getSeatNumber()).toList()
        );
    }

    private String buildCancellationEmail(Booking booking) {
        return String.format(
            "Dear %s,\n\n" +
            "Your booking for %s has been cancelled.\n" +
            "Refund will be processed within 3-5 business days.\n\n" +
            "Thank you!",
            booking.getUser().getFullName(),
            booking.getMovie().getTitle()
        );
    }

    private void sendEmail(String to, String subject, String body) {
        log.info("=== EMAIL SENT ===");
        log.info("To: {}", to);
        log.info("Subject: {}", subject);
        log.info("Body: {}", body);
    }
}