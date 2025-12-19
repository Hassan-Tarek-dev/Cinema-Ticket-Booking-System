package com.cinema.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.cinema.entity.Movie;
import com.cinema.entity.Review;
import com.cinema.entity.Showtime;
import com.cinema.repository.MovieRepository;
import com.cinema.repository.ReviewRepository;
import com.cinema.repository.ShowtimeRepository;
import com.cinema.service.MovieFilterContext;
import com.cinema.service.MovieService;

// STRATEGY PATTERN
@Controller
@RequestMapping("/movies")
public class MovieController {

    @Autowired
    private MovieRepository movieRepo;

    @Autowired
    private MovieService movieService;

    @Autowired
    private ShowtimeRepository showtimeRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private MovieFilterContext filterContext;

    @GetMapping
    public String listMovies(
            @RequestParam(required = false) String genre,
            @RequestParam(required = false) Double minRating,
            Model model) {
        
        List<Movie> movies;
        String selectedGenre = null;

        if (genre != null && !genre.isEmpty()) {
            // Strategy Pattern
            movies = filterContext.executeFilter("GENRE", genre);
            selectedGenre = genre;
        } else if (minRating != null) {
            // Strategy Pattern
            movies = filterContext.executeFilter("RATING", minRating.toString());
        } else {
            movies = movieService.getAllActiveMovies();
        }

        List<String> genres = movieRepo.findByIsActiveTrue().stream()
                .map(Movie::getGenre)
                .filter(g -> g != null && !g.isEmpty())
                .distinct()
                .collect(Collectors.toList());

        // Ensure lists are never null
        if (movies == null) {
            movies = List.of();
        }
        if (genres == null) {
            genres = List.of();
        }

        model.addAttribute("movies", movies);
        model.addAttribute("genres", genres);
        model.addAttribute("selectedGenre", selectedGenre);
        return "movies/movies";
    }

    @GetMapping("/genre/{genre}")
    public String filterByGenre(@PathVariable String genre, Model model) {
        // Strategy Pattern
        List<Movie> filteredMovies = filterContext.executeFilter("GENRE", genre);
        
        List<String> genres = movieRepo.findByIsActiveTrue().stream()
                .map(Movie::getGenre)
                .filter(g -> g != null && !g.isEmpty())
                .distinct()
                .collect(Collectors.toList());

        // Ensure lists are never null
        if (filteredMovies == null) {
            filteredMovies = List.of();
        }
        if (genres == null) {
            genres = List.of();
        }

        model.addAttribute("movies", filteredMovies);
        model.addAttribute("genres", genres);
        model.addAttribute("selectedGenre", genre);
        return "movies/movies";
    }

    @GetMapping("/rating/{minRating}")
    public String filterByRating(@PathVariable Double minRating, Model model) {
        // Strategy Pattern
        List<Movie> filteredMovies = filterContext.executeFilter("RATING", minRating.toString());
        
        List<String> genres = movieRepo.findByIsActiveTrue().stream()
                .map(Movie::getGenre)
                .filter(g -> g != null && !g.isEmpty())
                .distinct()
                .collect(Collectors.toList());

        // Ensure lists are never null
        if (filteredMovies == null) {
            filteredMovies = List.of();
        }
        if (genres == null) {
            genres = List.of();
        }

        model.addAttribute("movies", filteredMovies);
        model.addAttribute("genres", genres);
        model.addAttribute("selectedGenre", null);
        model.addAttribute("minRating", minRating);
        return "movies/movies";
    }

    @GetMapping("/{id}")
    public String movieDetails(@PathVariable Long id, Model model) {
        Movie movie = movieRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Movie not found"));
        
        List<Showtime> showtimes = showtimeRepository.findByMovie_Id(id);
        
        List<Review> reviews = reviewRepository.findByMovieId(id);
        if (reviews == null) {
            reviews = List.of();
        }
        
        Double movieRating = movie.getRating() != null ? movie.getRating() : 0.0;
        Double avgRating = reviews.isEmpty() 
            ? movieRating
            : reviews.stream()
                .filter(r -> r != null && r.getRating() != null)
                .mapToInt(Review::getRating)
                .average()
                .orElse(movieRating);

        if (showtimes == null) {
            showtimes = List.of();
        }

        model.addAttribute("movie", movie);
        model.addAttribute("showtimes", showtimes);
        model.addAttribute("reviews", reviews);
        model.addAttribute("avgRating", avgRating);
        return "movies/detail";
    }
}