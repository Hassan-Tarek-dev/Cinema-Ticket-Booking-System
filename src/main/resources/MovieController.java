package com.cinema.controller;

import com.cinema.model.Movie;
import com.cinema.service.MovieService;
import com.cinema.pattern.strategy.GenreFilterStrategy;
import com.cinema.pattern.strategy.AllMoviesStrategy;
import com.cinema.pattern.strategy.MovieFilterStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/movies")
public class MovieController {

    @Autowired
    private MovieService movieService;

    @GetMapping
    public String listMovies(@RequestParam(required = false) String genre, Model model) {
        // Strategy Pattern for Filtering
        MovieFilterStrategy strategy;
        String activeGenre = "All";

        if (genre != null && !genre.isEmpty() && !genre.equals("All")) {
            strategy = new GenreFilterStrategy(genre);
            activeGenre = genre;
        } else {
            strategy = new AllMoviesStrategy();
        }

        List<Movie> movies = strategy.filter(movieService.getAllMovies());

        model.addAttribute("movies", movies);
        model.addAttribute("selectedGenre", activeGenre);
        
        return "movies";
    }

    @GetMapping("/details")
    public String movieDetails(@RequestParam Long id, Model model) {
        model.addAttribute("movie", movieService.getMovieById(id));
        return "movie-details";
    }
}