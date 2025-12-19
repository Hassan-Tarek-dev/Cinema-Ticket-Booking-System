package com.cinema.observer;

import com.cinema.entity.Booking;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

// ADAPTER PATTERN
@Component
public class NotificationAdapter {

    private static final Logger log = LoggerFactory.getLogger(NotificationAdapter.class);

    private final EmailNotificationObserver emailObserver;
    private final SmsNotificationObserver smsObserver;

    public NotificationAdapter() {
        this.emailObserver = new EmailNotificationObserver();
        this.smsObserver = new SmsNotificationObserver();
    }

    public void sendNotification(Booking booking, String notificationType) {
        log.info("Adapter: Sending {} notification for booking: {}", notificationType, booking.getId());

        switch (notificationType.toUpperCase()) {
            case "CONFIRMED" -> {
                emailObserver.onBookingConfirmed(booking);
                smsObserver.onBookingConfirmed(booking);
            }
            case "CANCELLED" -> {
                emailObserver.onBookingCancelled(booking);
                smsObserver.onBookingCancelled(booking);
            }
            default -> {
                log.warn("Unknown notification type: {}", notificationType);
                emailObserver.onBookingConfirmed(booking);
                smsObserver.onBookingConfirmed(booking);
            }
        }
    }

    public void sendViaEmail(Booking booking, String notificationType) {
        log.info("Adapter: Sending email notification for booking: {}", booking.getId());
        if ("CONFIRMED".equalsIgnoreCase(notificationType)) {
            emailObserver.onBookingConfirmed(booking);
        } else if ("CANCELLED".equalsIgnoreCase(notificationType)) {
            emailObserver.onBookingCancelled(booking);
        }
    }

    public void sendViaSms(Booking booking, String notificationType) {
        log.info("Adapter: Sending SMS notification for booking: {}", booking.getId());
        if ("CONFIRMED".equalsIgnoreCase(notificationType)) {
            smsObserver.onBookingConfirmed(booking);
        } else if ("CANCELLED".equalsIgnoreCase(notificationType)) {
            smsObserver.onBookingCancelled(booking);
        }
    }
}
