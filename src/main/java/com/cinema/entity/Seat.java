package com.cinema.entity;

import jakarta.persistence.*;

// STATE PATTERN
@Entity
@Table(name = "seats")
public class Seat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "showtime_id", nullable = false)
    private Showtime showtime;

    @Column(nullable = false)
    private String seatNumber;

    @Column(nullable = false)
    private Integer rowNumber;

    @Column(nullable = false)
    private Integer columnNumber;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SeatStatus status = SeatStatus.AVAILABLE;

    @ManyToOne
    @JoinColumn(name = "booking_id")
    private Booking booking;

    // STATE PATTERN
    public enum SeatStatus {
        AVAILABLE,
        RESERVED,
        SOLD
    }

    public Seat() {}

    public Seat(Showtime showtime, String seatNumber, Integer rowNumber, Integer columnNumber) {
        this.showtime = showtime;
        this.seatNumber = seatNumber;
        this.rowNumber = rowNumber;
        this.columnNumber = columnNumber;
        this.status = SeatStatus.AVAILABLE;
    }

    public Seat(Long id, Showtime showtime, String seatNumber, Integer rowNumber,
                Integer columnNumber, SeatStatus status, Booking booking) {
        this.id = id;
        this.showtime = showtime;
        this.seatNumber = seatNumber;
        this.rowNumber = rowNumber;
        this.columnNumber = columnNumber;
        this.status = status;
        this.booking = booking;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Showtime getShowtime() {
        return showtime;
    }

    public void setShowtime(Showtime showtime) {
        this.showtime = showtime;
    }

    public String getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(String seatNumber) {
        this.seatNumber = seatNumber;
    }

    public Integer getRowNumber() {
        return rowNumber;
    }

    public void setRowNumber(Integer rowNumber) {
        this.rowNumber = rowNumber;
    }

    public Integer getColumnNumber() {
        return columnNumber;
    }

    public void setColumnNumber(Integer columnNumber) {
        this.columnNumber = columnNumber;
    }

    public SeatStatus getStatus() {
        return status;
    }

    public void setStatus(SeatStatus status) {
        this.status = status;
    }

    public Booking getBooking() {
        return booking;
    }

    public void setBooking(Booking booking) {
        this.booking = booking;
    }

    public void reserveSeat() {
        if (this.status == SeatStatus.AVAILABLE) {
            this.status = SeatStatus.RESERVED;
        } else {
            throw new IllegalStateException("Cannot reserve seat with status: " + this.status);
        }
    }

    public void confirmSeat() {
        if (this.status == SeatStatus.RESERVED || this.status == SeatStatus.AVAILABLE) {
            this.status = SeatStatus.SOLD;
        } else {
            throw new IllegalStateException("Cannot confirm seat with status: " + this.status);
        }
    }

    public void releaseSeat() {
        if (this.status != SeatStatus.SOLD) {
            this.status = SeatStatus.AVAILABLE;
            this.booking = null;
        } else {
            throw new IllegalStateException("Cannot release a sold seat");
        }
    }

    public boolean isAvailable() {
        return this.status == SeatStatus.AVAILABLE;
    }
}
