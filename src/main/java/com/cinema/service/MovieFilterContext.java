package com.cinema.service;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.cinema.entity.Movie;
import com.cinema.repository.MovieRepository;

// STRATEGY PATTERN
@Component
public class MovieFilterContext {
    private static final Logger log = LoggerFactory.getLogger(MovieFilterContext.class);

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private GenreFilterStrategy genreFilterStrategy;

    @Autowired
    private RatingFilterStrategy ratingFilterStrategy;

    public List<Movie> executeFilter(String filterType, String filterValue) {
        log.info("Executing filter: {} with value: {}", filterType, filterValue);
        List<Movie> allMovies = movieRepository.findByIsActiveTrue();
        
        return switch (filterType.toUpperCase()) {
            case "GENRE" -> {
                genreFilterStrategy.setGenre(filterValue);
                yield genreFilterStrategy.filter(allMovies);
            }
            case "RATING" -> {
                try {
                    Double minRating = Double.parseDouble(filterValue);
                    ratingFilterStrategy.setMinRating(minRating);
                    yield ratingFilterStrategy.filter(allMovies);
                } catch (NumberFormatException e) {
                    log.error("Invalid rating value: {}", filterValue);
                    yield allMovies;
                }
            }
            default -> {
                log.warn("Unknown filter type: {}", filterType);
                yield allMovies;
            }
        };
    }
}
