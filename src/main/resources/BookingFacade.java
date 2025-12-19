package com.cinema.service;

import com.cinema.model.Booking;
import com.cinema.model.Movie;
import com.cinema.repository.BookingRepository;
import com.cinema.repository.MovieRepository;
import com.cinema.pattern.factory.PaymentFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BookingFacade {

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private PaymentFactory paymentFactory;

    @Autowired
    private NotificationManager notificationManager; // Existing Observer

    @Transactional
    public Booking placeBooking(String username, Long movieId, String seat, String paymentType) {
        // 1. Retrieve Movie
        Movie movie = movieRepository.findById(movieId)
                .orElseThrow(() -> new RuntimeException("Movie not found"));

        // 2. Process Payment (Factory)
        paymentFactory.getPaymentMethod(paymentType).pay(movie.getPrice());

        // 3. Create Booking (Builder)
        Booking booking = new Booking.BookingBuilder()
                .setUser(username)
                .setMovie(movie.getTitle())
                .setSeat(seat)
                .setPayment(movie.getPrice(), paymentType)
                .build();

        // 4. Save & Notify (Observer)
        Booking savedBooking = bookingRepository.save(booking);
        notificationManager.notifyUser(username, "Booking Confirmed for " + movie.getTitle());

        return savedBooking;
    }
}