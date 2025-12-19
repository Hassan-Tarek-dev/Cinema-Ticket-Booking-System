package com.cinema.service;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import com.cinema.entity.Movie;

// STRATEGY PATTERN
@Component("ratingFilterStrategy")
public class RatingFilterStrategy implements MovieFilterStrategy {
    private static final Logger log = LoggerFactory.getLogger(RatingFilterStrategy.class);
    private Double minRating;

    public void setMinRating(Double minRating) {
        this.minRating = minRating;
    }

    @Override
    public List<Movie> filter(List<Movie> movies) {
        log.info("Filtering movies by minimum rating: {}", minRating);
        return movies.stream()
                .filter(m -> m.getRating() >= minRating)
                .toList();
    }

    @Override
    public String getFilterType() {
        return "RATING";
    }
}
