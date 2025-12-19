package com.cinema.dto;

public class SeatDTO {
    private Long id;
    private Long showtimeId;
    private String seatNumber;
    private Integer rowNumber;
    private Integer columnNumber;
    private String status;
    private Long bookingId;

    public SeatDTO() {}


    public SeatDTO(Long id, Long showtimeId, String seatNumber, 
                   Integer rowNumber, Integer columnNumber, String status, Long bookingId) {
        this.id = id;
        this.showtimeId = showtimeId;
        this.seatNumber = seatNumber;
        this.rowNumber = rowNumber;
        this.columnNumber = columnNumber;
        this.status = status;
        this.bookingId = bookingId;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getShowtimeId() { return showtimeId; }
    public void setShowtimeId(Long showtimeId) { this.showtimeId = showtimeId; }

    public String getSeatNumber() { return seatNumber; }
    public void setSeatNumber(String seatNumber) { this.seatNumber = seatNumber; }

    public Integer getRowNumber() { return rowNumber; }
    public void setRowNumber(Integer rowNumber) { this.rowNumber = rowNumber; }

    public Integer getColumnNumber() { return columnNumber; }
    public void setColumnNumber(Integer columnNumber) { this.columnNumber = columnNumber; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public Long getBookingId() { return bookingId; }
    public void setBookingId(Long bookingId) { this.bookingId = bookingId; }
}