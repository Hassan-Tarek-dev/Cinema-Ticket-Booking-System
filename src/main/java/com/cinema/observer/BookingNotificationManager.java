package com.cinema.observer;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.cinema.entity.Booking;

// OBSERVER PATTERN
@Component
public class BookingNotificationManager {

    private static final Logger log = LoggerFactory.getLogger(BookingNotificationManager.class);

    private final List<BookingObserver> observers = new ArrayList<>();

    public void registerObserver(BookingObserver observer) {
        if (!observers.contains(observer)) {
            observers.add(observer);
            log.info("Observer registered: {}", observer.getClass().getSimpleName());
        }
    }

    public void removeObserver(BookingObserver observer) {
        observers.remove(observer);
    }

    public void notifyBookingConfirmed(Booking booking) {
        log.info("Notifying {} observers about booking confirmation", observers.size());
        for (BookingObserver observer : observers) {
            observer.onBookingConfirmed(booking);
        }
    }

    public void notifyBookingCancelled(Booking booking) {
        log.info("Notifying {} observers about booking cancellation", observers.size());
        for (BookingObserver observer : observers) {
            observer.onBookingCancelled(booking);
        }
    }

    public int getObserverCount() {
        return observers.size();
    }
}