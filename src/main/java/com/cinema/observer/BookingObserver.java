package com.cinema.observer;

import com.cinema.entity.Booking;

// OBSERVER PATTERN
public interface BookingObserver {
    void onBookingConfirmed(Booking booking);

    void onBookingCancelled(Booking booking);
}
