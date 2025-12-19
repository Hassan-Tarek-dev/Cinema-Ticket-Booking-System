package com.cinema.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

// BUILDER PATTERN
public class BookingBuilder {
    private User user;
    private Movie movie;
    private Showtime showtime;
    private List<Seat> seats;
    private Double totalPrice;
    private Booking.PaymentMethod paymentMethod;
    private LocalDateTime bookingDate;

    public BookingBuilder() {}

    public BookingBuilder(User user, Movie movie, Showtime showtime, List<Seat> seats,
                         Double totalPrice, Booking.PaymentMethod paymentMethod, LocalDateTime bookingDate) {
        this.user = user;
        this.movie = movie;
        this.showtime = showtime;
        this.seats = seats;
        this.totalPrice = totalPrice;
        this.paymentMethod = paymentMethod;
        this.bookingDate = bookingDate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public Showtime getShowtime() {
        return showtime;
    }

    public void setShowtime(Showtime showtime) {
        this.showtime = showtime;
    }

    public List<Seat> getSeats() {
        return seats;
    }

    public void setSeats(List<Seat> seats) {
        this.seats = seats;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Booking.PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(Booking.PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public LocalDateTime getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(LocalDateTime bookingDate) {
        this.bookingDate = bookingDate;
    }

    public Booking build() {
        Booking booking = new Booking();
        booking.setUser(this.user);
        booking.setMovie(this.movie);
        booking.setShowtime(this.showtime);
        booking.setSeats(this.seats != null ? this.seats : new ArrayList<>());
        booking.setTotalPrice(this.totalPrice);
        booking.setPaymentMethod(this.paymentMethod);
        booking.setBookingDate(this.bookingDate != null ? this.bookingDate : LocalDateTime.now());
        booking.setStatus(Booking.BookingStatus.PENDING);

        return booking;
    }

    public BookingBuilder withUser(User user) {
        this.user = user;
        return this;
    }

    public BookingBuilder withMovie(Movie movie) {
        this.movie = movie;
        return this;
    }

    public BookingBuilder withShowtime(Showtime showtime) {
        this.showtime = showtime;
        return this;
    }

    public BookingBuilder withSeats(List<Seat> seats) {
        this.seats = seats;
        return this;
    }

    public BookingBuilder withTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
        return this;
    }

    public BookingBuilder withPaymentMethod(Booking.PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
        return this;
    }

    public BookingBuilder withBookingDate(LocalDateTime bookingDate) {
        this.bookingDate = bookingDate;
        return this;
    }
}
