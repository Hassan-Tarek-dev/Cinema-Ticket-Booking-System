package com.cinema.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cinema.dto.SeatDTO;
import com.cinema.entity.Seat;
import com.cinema.repository.SeatRepository;

// SINGLETON PATTERN
@Service
public class SeatService {

    private static final Logger log = LoggerFactory.getLogger(SeatService.class);

    @Autowired
    private SeatRepository seatRepository;

    public List<Seat> getAvailableSeats(Long showtimeId) {
        log.info("Fetching available seats for showtime ID: {}", showtimeId);
        return seatRepository.findByShowtimeIdAndStatus(showtimeId, Seat.SeatStatus.AVAILABLE);
    }

    public List<Seat> getSeatsByShowtimeId(Long showtimeId) {
        log.info("Fetching all seats for showtime ID: {}", showtimeId);
        return seatRepository.findByShowtimeId(showtimeId);
    }

    public Optional<Seat> getSeatById(Long id) {
        log.info("Fetching seat with ID: {}", id);
        return seatRepository.findById(id);
    }

    @Transactional
    public boolean reserveSeat(Long seatId) {
        log.info("Reserving seat ID: {}", seatId);
        Optional<Seat> seat = seatRepository.findById(seatId);
        if (seat.isEmpty()) {
            log.warn("Seat not found with ID: {}", seatId);
            return false;
        }

        try {
            seat.get().reserveSeat();
            seatRepository.save(seat.get());
            log.info("Seat reserved successfully: {}", seatId);
            return true;
        } catch (IllegalStateException e) {
            log.error("Cannot reserve seat: {}", e.getMessage());
            return false;
        }
    }

    @Transactional
    public boolean confirmSeat(Long seatId) {
        log.info("Confirming seat ID: {}", seatId);
        Optional<Seat> seat = seatRepository.findById(seatId);
        if (seat.isEmpty()) {
            log.warn("Seat not found with ID: {}", seatId);
            return false;
        }

        try {
            seat.get().confirmSeat();
            seatRepository.save(seat.get());
            log.info("Seat confirmed successfully: {}", seatId);
            return true;
        } catch (IllegalStateException e) {
            log.error("Cannot confirm seat: {}", e.getMessage());
            return false;
        }
    }

    @Transactional
    public boolean releaseSeat(Long seatId) {
        log.info("Releasing seat ID: {}", seatId);
        Optional<Seat> seat = seatRepository.findById(seatId);
        if (seat.isEmpty()) {
            log.warn("Seat not found with ID: {}", seatId);
            return false;
        }

        try {
            seat.get().releaseSeat();
            seatRepository.save(seat.get());
            log.info("Seat released successfully: {}", seatId);
            return true;
        } catch (IllegalStateException e) {
            log.error("Cannot release seat: {}", e.getMessage());
            return false;
        }
    }

    public Seat createSeat(Seat seat) {
        log.info("Creating new seat: {}", seat.getSeatNumber());
        return seatRepository.save(seat);
    }

   @Transactional
public void initializeSeatsForShowtime(com.cinema.entity.Showtime showtime) {
    log.info("Initializing seats for showtime ID: {} with {} total seats", 
             showtime.getId(), showtime.getTotalSeats());
    
    seatRepository.deleteByShowtimeId(showtime.getId());

    List<Seat> seatsToSave = new ArrayList<>();
    Integer totalSeats = showtime.getTotalSeats();
    int seatsPerRow = 10; 
    int numRows = (int) Math.ceil((double) totalSeats / seatsPerRow);
    
    int seatCounter = 1;
    for (int row = 1; row <= numRows; row++) {
        for (int col = 1; col <= seatsPerRow && seatCounter <= totalSeats; col++) {
            String seatNumber = String.valueOf((char) ('A' + row - 1)) + col;
            Seat seat = new Seat();
            seat.setShowtime(showtime);
            seat.setSeatNumber(seatNumber);
            seat.setRowNumber(row);
            seat.setColumnNumber(col);
            seat.setStatus(Seat.SeatStatus.AVAILABLE);
            seatsToSave.add(seat);
            seatCounter++;
        }
    }
    
    seatRepository.saveAll(seatsToSave);
    log.info("Initialized {} seats for showtime ID: {}", seatsToSave.size(), showtime.getId());
}
    public SeatDTO convertToDTO(Seat seat) {
        SeatDTO dto = new SeatDTO();
        dto.setId(seat.getId());
        dto.setShowtimeId(seat.getShowtime().getId());
        dto.setSeatNumber(seat.getSeatNumber());
        dto.setRowNumber(seat.getRowNumber());
        dto.setColumnNumber(seat.getColumnNumber());
        dto.setStatus(seat.getStatus().name());
        dto.setBookingId(seat.getBooking() != null ? seat.getBooking().getId() : null);
        return dto;
    }
}