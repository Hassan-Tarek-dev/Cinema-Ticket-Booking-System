package com.cinema.dto;

import java.time.LocalDateTime;

public class ShowtimeDTO {
    private Long id;
    private Long movieId;
    private LocalDateTime showTime;
    private Double ticketPrice;
    private Integer totalSeats;
    private Integer availableSeats;
    private String hall;

    public ShowtimeDTO() {}

    public ShowtimeDTO(Long id, Long movieId, LocalDateTime showTime, Double ticketPrice, 
                       Integer totalSeats, Integer availableSeats, String hall) {
        this.id = id;
        this.movieId = movieId;
        this.showTime = showTime;
        this.ticketPrice = ticketPrice;
        this.totalSeats = totalSeats;
        this.availableSeats = availableSeats;
        this.hall = hall;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getMovieId() { return movieId; }
    public void setMovieId(Long movieId) { this.movieId = movieId; }

    public LocalDateTime getShowTime() { return showTime; }
    public void setShowTime(LocalDateTime showTime) { this.showTime = showTime; }

    public Double getTicketPrice() { return ticketPrice; }
    public void setTicketPrice(Double ticketPrice) { this.ticketPrice = ticketPrice; }

    public Integer getTotalSeats() { return totalSeats; }
    public void setTotalSeats(Integer totalSeats) { this.totalSeats = totalSeats; }

    public Integer getAvailableSeats() { return availableSeats; }
    public void setAvailableSeats(Integer availableSeats) { this.availableSeats = availableSeats; }

    public String getHall() { return hall; }
    public void setHall(String hall) { this.hall = hall; }
}