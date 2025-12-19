package com.cinema.service;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import com.cinema.entity.Movie;

// STRATEGY PATTERN
@Component("genreFilterStrategy")
public class GenreFilterStrategy implements MovieFilterStrategy {
    private static final Logger log = LoggerFactory.getLogger(GenreFilterStrategy.class);
    private String genre;

    public void setGenre(String genre) {
        this.genre = genre;
    }

    @Override
    public List<Movie> filter(List<Movie> movies) {
        log.info("Filtering movies by genre: {}", genre);
        return movies.stream()
                .filter(m -> m.getGenre().equalsIgnoreCase(genre))
                .toList();
    }

    @Override
    public String getFilterType() {
        return "GENRE";
    }
}
