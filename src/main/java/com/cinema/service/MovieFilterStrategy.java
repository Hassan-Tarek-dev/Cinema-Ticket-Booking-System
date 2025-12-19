package com.cinema.service;

import java.util.List;
import com.cinema.entity.Movie;

// STRATEGY PATTERN
public interface MovieFilterStrategy {
    List<Movie> filter(List<Movie> movies);
    String getFilterType();
}
