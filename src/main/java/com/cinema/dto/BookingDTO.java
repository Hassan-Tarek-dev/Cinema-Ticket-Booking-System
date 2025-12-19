package com.cinema.dto;

import java.time.LocalDateTime;
import java.util.List;

public class BookingDTO {
    private Long id;
    private Long userId;
    private Long movieId;
    private Long showtimeId;
    private List<Long> seatIds;
    private Double totalPrice;
    private String status;
    private String paymentMethod;
    private LocalDateTime bookingDate;
    private LocalDateTime paymentDate;
    private String transactionId;
    private MovieDTO movie;
    private ShowtimeDTO showtime;

    public BookingDTO() {}

    public BookingDTO(Long id, Long userId, Long movieId, Long showtimeId, List<Long> seatIds, 
                      Double totalPrice, String status, String paymentMethod, 
                      LocalDateTime bookingDate, LocalDateTime paymentDate, String transactionId) {
        this.id = id;
        this.userId = userId;
        this.movieId = movieId;
        this.showtimeId = showtimeId;
        this.seatIds = seatIds;
        this.totalPrice = totalPrice;
        this.status = status;
        this.paymentMethod = paymentMethod;
        this.bookingDate = bookingDate;
        this.paymentDate = paymentDate;
        this.transactionId = transactionId;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public Long getMovieId() { return movieId; }
    public void setMovieId(Long movieId) { this.movieId = movieId; }

    public Long getShowtimeId() { return showtimeId; }
    public void setShowtimeId(Long showtimeId) { this.showtimeId = showtimeId; }

    public List<Long> getSeatIds() { return seatIds; }
    public void setSeatIds(List<Long> seatIds) { this.seatIds = seatIds; }

    public Double getTotalPrice() { return totalPrice; }
    public void setTotalPrice(Double totalPrice) { this.totalPrice = totalPrice; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getPaymentMethod() { return paymentMethod; }
    public void setPaymentMethod(String paymentMethod) { this.paymentMethod = paymentMethod; }

    public LocalDateTime getBookingDate() { return bookingDate; }
    public void setBookingDate(LocalDateTime bookingDate) { this.bookingDate = bookingDate; }

    public LocalDateTime getPaymentDate() { return paymentDate; }
    public void setPaymentDate(LocalDateTime paymentDate) { this.paymentDate = paymentDate; }

    public String getTransactionId() { return transactionId; }
    public void setTransactionId(String transactionId) { this.transactionId = transactionId; }

    public MovieDTO getMovie() { return movie; }
    public void setMovie(MovieDTO movie) { this.movie = movie; }

    public ShowtimeDTO getShowtime() { return showtime; }
    public void setShowtime(ShowtimeDTO showtime) { this.showtime = showtime; }
}