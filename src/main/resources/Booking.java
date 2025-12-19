package com.cinema.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "bookings")
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String movieTitle;
    private String seatNumber;
    private double amount;
    private LocalDateTime bookingTime;
    private String paymentMethod;

    // Private constructor to force use of Builder
    private Booking(BookingBuilder builder) {
        this.username = builder.username;
        this.movieTitle = builder.movieTitle;
        this.seatNumber = builder.seatNumber;
        this.amount = builder.amount;
        this.paymentMethod = builder.paymentMethod;
        this.bookingTime = LocalDateTime.now();
    }

    public Booking() {} // JPA requirement

    // Getters
    public Long getId() { return id; }
    public String getUsername() { return username; }
    public String getMovieTitle() { return movieTitle; }
    public String getSeatNumber() { return seatNumber; }
    public double getAmount() { return amount; }

    // Builder Pattern
    public static class BookingBuilder {
        private String username;
        private String movieTitle;
        private String seatNumber;
        private double amount;
        private String paymentMethod;

        public BookingBuilder setUser(String username) {
            this.username = username;
            return this;
        }

        public BookingBuilder setMovie(String movieTitle) {
            this.movieTitle = movieTitle;
            return this;
        }

        public BookingBuilder setSeat(String seatNumber) {
            this.seatNumber = seatNumber;
            return this;
        }

        public BookingBuilder setPayment(double amount, String method) {
            this.amount = amount;
            this.paymentMethod = method;
            return this;
        }

        public Booking build() {
            return new Booking(this);
        }
    }
}