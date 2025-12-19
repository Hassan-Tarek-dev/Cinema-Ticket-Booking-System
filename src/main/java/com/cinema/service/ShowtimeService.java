package com.cinema.service;

import com.cinema.entity.Showtime;
import com.cinema.dto.ShowtimeDTO;
import com.cinema.repository.ShowtimeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

// SINGLETON PATTERN
@Service
public class ShowtimeService {

    private static final Logger log = LoggerFactory.getLogger(ShowtimeService.class);

    @Autowired
    private ShowtimeRepository showtimeRepository;

    public List<Showtime> getShowtimesByMovieId(Long movieId) {
        log.info("Fetching showtimes for movie ID: {}", movieId);
        return showtimeRepository.findByMovie_Id(movieId);
    }

    public List<Showtime> getUpcomingShowtimes() {
        log.info("Fetching upcoming showtimes");
        return showtimeRepository.findByShowTimeAfter(LocalDateTime.now());
    }

    public Optional<Showtime> getShowtimeById(Long id) {
        log.info("Fetching showtime with ID: {}", id);
        return showtimeRepository.findById(id);
    }

    public Showtime createShowtime(Showtime showtime) {
        log.info("Creating new showtime for movie ID: {}", showtime.getMovie().getId());
        showtime.setAvailableSeats(showtime.getTotalSeats());
        return showtimeRepository.save(showtime);
    }

    public void updateAvailableSeats(Long showtimeId, Integer newCount) {
        log.info("Updating available seats for showtime ID: {} to: {}", showtimeId, newCount);
        Optional<Showtime> showtime = showtimeRepository.findById(showtimeId);
        if (showtime.isPresent()) {
            showtime.get().setAvailableSeats(newCount);
            showtimeRepository.save(showtime.get());
        }
    }

    public ShowtimeDTO convertToDTO(Showtime showtime) {
        ShowtimeDTO dto = new ShowtimeDTO();
        dto.setId(showtime.getId());
        dto.setMovieId(showtime.getMovie().getId());
        dto.setShowTime(showtime.getShowTime());
        dto.setTicketPrice(showtime.getTicketPrice());
        dto.setTotalSeats(showtime.getTotalSeats());
        dto.setAvailableSeats(showtime.getAvailableSeats());
        dto.setHall(showtime.getHall());
        return dto;
    }
}
