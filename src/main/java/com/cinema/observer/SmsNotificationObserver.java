package com.cinema.observer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.cinema.entity.Booking;

// OBSERVER PATTERN
@Component
public class SmsNotificationObserver implements BookingObserver {

    private static final Logger log = LoggerFactory.getLogger(SmsNotificationObserver.class);

    @Override
    public void onBookingConfirmed(Booking booking) {
        log.info("Sending confirmation SMS");
        
        String message = String.format(
            "Your booking for %s is confirmed. Showtime: %s. Seats: %s. Price: Rs.%.2f",
            booking.getMovie().getTitle(),
            booking.getShowtime().getShowTime(),
            booking.getSeats().stream().map(s -> s.getSeatNumber()).toList(),
            booking.getTotalPrice()
        );
        
        sendSms(message);
        log.info("Confirmation SMS sent successfully");
    }

    @Override
    public void onBookingCancelled(Booking booking) {
        log.info("Sending cancellation SMS");
        
        String message = String.format(
            "Your booking for %s has been cancelled. Refund will be processed soon.",
            booking.getMovie().getTitle()
        );
        
        sendSms(message);
        log.info("Cancellation SMS sent successfully");
    }

    private void sendSms(String message) {
        log.info("=== SMS SENT ===");
        log.info("Message: {}", message);
    }
}