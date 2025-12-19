package com.cinema.pattern.strategy;

import com.cinema.model.Movie;
import java.util.List;
import java.util.stream.Collectors;

public interface MovieFilterStrategy {
    List<Movie> filter(List<Movie> movies);
}

class GenreFilterStrategy implements MovieFilterStrategy {
    private final String genre;

    public GenreFilterStrategy(String genre) {
        this.genre = genre;
    }

    @Override
    public List<Movie> filter(List<Movie> movies) {
        return movies.stream()
                .filter(m -> m.getGenre().equalsIgnoreCase(genre))
                .collect(Collectors.toList());
    }
}

class AllMoviesStrategy implements MovieFilterStrategy {
    @Override
    public List<Movie> filter(List<Movie> movies) {
        return movies;
    }
}