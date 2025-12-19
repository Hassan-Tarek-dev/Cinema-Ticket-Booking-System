package com.cinema.service;

import com.cinema.entity.Movie;
import com.cinema.dto.MovieDTO;
import com.cinema.repository.MovieRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

// SINGLETON PATTERN
@Service
public class MovieService {

    private static final Logger log = LoggerFactory.getLogger(MovieService.class);

    @Autowired
    private MovieRepository movieRepository;

    public List<Movie> getAllActiveMovies() {
        log.info("Fetching all active movies");
        return movieRepository.findByIsActiveTrue();
    }

    public Optional<Movie> getMovieById(Long id) {
        log.info("Fetching movie with ID: {}", id);
        return movieRepository.findById(id);
    }

    public List<Movie> getMoviesByGenre(String genre) {
        log.info("Fetching movies by genre: {}", genre);
        return movieRepository.findByGenre(genre);
    }

    public Movie createMovie(Movie movie) {
        log.info("Creating new movie: {}", movie.getTitle());
        return movieRepository.save(movie);
    }

    public Movie updateMovie(Long id, Movie movieDetails) {
        log.info("Updating movie with ID: {}", id);
        Optional<Movie> movie = movieRepository.findById(id);
        if (movie.isPresent()) {
            Movie m = movie.get();
            m.setTitle(movieDetails.getTitle());
            m.setDescription(movieDetails.getDescription());
            m.setGenre(movieDetails.getGenre());
            m.setRating(movieDetails.getRating());
            return movieRepository.save(m);
        }
        return null;
    }

    public MovieDTO convertToDTO(Movie movie) {
        MovieDTO dto = new MovieDTO();
        dto.setId(movie.getId());
        dto.setTitle(movie.getTitle());
        dto.setDescription(movie.getDescription());
        dto.setGenre(movie.getGenre());
        dto.setDurationMinutes(movie.getDurationMinutes());
        dto.setPosterUrl(movie.getPosterUrl());
        dto.setRating(movie.getRating());
        dto.setReleaseDate(movie.getReleaseDate());
        dto.setIsActive(movie.getIsActive());
        return dto;
    }
}
