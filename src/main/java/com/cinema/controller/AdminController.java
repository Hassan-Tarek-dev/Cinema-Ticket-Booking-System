package com.cinema.controller;

import com.cinema.entity.Movie;
import com.cinema.entity.Showtime;
import com.cinema.entity.Seat;
import com.cinema.service.MovieService;
import com.cinema.service.ShowtimeService;
import com.cinema.service.SeatService;
import com.cinema.repository.MovieRepository;
import com.cinema.repository.ShowtimeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;
import java.util.List;

// PROXY PATTERN
@SuppressWarnings("unused")
@Controller
@RequestMapping("/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    private static final Logger log = LoggerFactory.getLogger(AdminController.class);

    @Autowired
    private MovieService movieService;

    @Autowired
    private ShowtimeService showtimeService;

    @Autowired
    private SeatService seatService;

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private ShowtimeRepository showtimeRepository;

    @GetMapping
    public String dashboard(Model model) {
        log.info("Admin dashboard accessed");
        model.addAttribute("totalMovies", movieRepository.count());
        model.addAttribute("totalShowtimes", showtimeRepository.count());
        model.addAttribute("activeMovies", movieRepository.findByIsActiveTrue().size());
        return "admin/dashboard";
    }

    @GetMapping("/movies")
    public String listMovies(Model model) {
        log.info("Admin: Listing all movies");
        model.addAttribute("movies", movieRepository.findAll());
        return "admin/movies";
    }

    @GetMapping("/movies/new")
    public String showMovieForm(Model model) {
        model.addAttribute("movie", new Movie());
        return "admin/movie-form";
    }

    @PostMapping("/movies")
    public String createMovie(@ModelAttribute Movie movie, RedirectAttributes redirectAttributes) {
        log.info("Admin: Creating movie: {}", movie.getTitle());
        movie.setIsActive(true);
        if (movie.getReleaseDate() == null) {
            movie.setReleaseDate(LocalDateTime.now());
        }
        movieService.createMovie(movie);
        redirectAttributes.addFlashAttribute("success", "Movie created successfully!");
        return "redirect:/admin/movies";
    }

    @GetMapping("/movies/{id}/edit")
    public String editMovieForm(@PathVariable Long id, Model model) {
        Movie movie = movieRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Movie not found"));
        model.addAttribute("movie", movie);
        return "admin/movie-form";
    }

    @PostMapping("/movies/{id}")
    public String updateMovie(@PathVariable Long id, @ModelAttribute Movie movieDetails, 
                             RedirectAttributes redirectAttributes) {
        log.info("Admin: Updating movie: {}", id);
        movieService.updateMovie(id, movieDetails);
        redirectAttributes.addFlashAttribute("success", "Movie updated successfully!");
        return "redirect:/admin/movies";
    }

    @PostMapping("/movies/{id}/delete")
    public String deleteMovie(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        log.info("Admin: Deleting movie: {}", id);
        movieRepository.deleteById(id);
        redirectAttributes.addFlashAttribute("success", "Movie deleted successfully!");
        return "redirect:/admin/movies";
    }

    @PostMapping("/movies/{id}/toggle")
    public String toggleMovieStatus(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        log.info("Admin: Toggling movie status: {}", id);
        Movie movie = movieRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Movie not found"));
        movie.setIsActive(!movie.getIsActive());
        movieRepository.save(movie);
        redirectAttributes.addFlashAttribute("success", 
            "Movie " + (movie.getIsActive() ? "activated" : "deactivated") + " successfully!");
        return "redirect:/admin/movies";
    }

    @GetMapping("/showtimes")
    public String listShowtimes(Model model) {
        log.info("Admin: Listing all showtimes");
        model.addAttribute("showtimes", showtimeRepository.findAll());
        model.addAttribute("movies", movieRepository.findByIsActiveTrue());
        return "admin/showtimes";
    }

    @GetMapping("/showtimes/new")
    public String showShowtimeForm(Model model) {
        try {
            log.info("Admin: Showing new showtime form");
            Showtime showtime = new Showtime();
            List<Movie> movies;
            try {
                movies = movieService.getAllActiveMovies();
            } catch (Exception e) {
                log.error("Error fetching movies: ", e);
                movies = List.of();
            }
            
            if (movies == null || movies.isEmpty()) {
                movies = List.of();
                log.warn("No active movies found. Showing empty dropdown.");
            }
            model.addAttribute("showtime", showtime);
            model.addAttribute("movies", movies);
            log.info("Admin: Form prepared with {} movies", movies.size());
            return "admin/showtime-form";
        } catch (Exception e) {
            log.error("Error in showShowtimeForm: ", e);
            model.addAttribute("error", "Error loading form: " + e.getMessage());
            return "redirect:/admin/showtimes";
        }
    }

    @PostMapping("/showtimes")
    public String createShowtime(@ModelAttribute Showtime showtime, 
                                @RequestParam Long movieId,
                                RedirectAttributes redirectAttributes) {
        log.info("Admin: Creating showtime for movie: {}", movieId);
        Movie movie = movieRepository.findById(movieId)
                .orElseThrow(() -> new RuntimeException("Movie not found"));
        showtime.setMovie(movie);
        showtime.setAvailableSeats(showtime.getTotalSeats());
        Showtime saved = showtimeRepository.save(showtime);
        
        seatService.initializeSeatsForShowtime(saved);
        
        redirectAttributes.addFlashAttribute("success", "Showtime created successfully!");
        return "redirect:/admin/showtimes";
    }

    @GetMapping("/showtimes/{id}/edit")
    public String editShowtimeForm(@PathVariable Long id, Model model) {
        try {
            log.info("Admin: Showing edit form for showtime: {}", id);
            Showtime showtime = showtimeRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Showtime not found"));
            List<Movie> movies;
            try {
                movies = movieService.getAllActiveMovies();
            } catch (Exception e) {
                log.error("Error fetching movies: ", e);
                movies = List.of();
            }
            
            if (movies == null || movies.isEmpty()) {
                movies = List.of();
                log.warn("No active movies found for edit form.");
            }
            model.addAttribute("showtime", showtime);
            model.addAttribute("movies", movies);
            return "admin/showtime-form";
        } catch (Exception e) {
            log.error("Error in editShowtimeForm: ", e);
            return "redirect:/admin/showtimes";
        }
    }

    @PostMapping("/showtimes/{id}")
    public String updateShowtime(@PathVariable Long id, @ModelAttribute Showtime showtimeDetails,
                                @RequestParam Long movieId,
                                RedirectAttributes redirectAttributes) {
        log.info("Admin: Updating showtime: {}", id);
        Showtime showtime = showtimeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Showtime not found"));
        Movie movie = movieRepository.findById(movieId)
                .orElseThrow(() -> new RuntimeException("Movie not found"));
        
        showtime.setMovie(movie);
        showtime.setShowTime(showtimeDetails.getShowTime());
        showtime.setHall(showtimeDetails.getHall());
        showtime.setTicketPrice(showtimeDetails.getTicketPrice());
        showtime.setTotalSeats(showtimeDetails.getTotalSeats());
        
        showtimeRepository.save(showtime);
        redirectAttributes.addFlashAttribute("success", "Showtime updated successfully!");
        return "redirect:/admin/showtimes";
    }

    @PostMapping("/showtimes/{id}/delete")
    public String deleteShowtime(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        log.info("Admin: Deleting showtime: {}", id);
        showtimeRepository.deleteById(id);
        redirectAttributes.addFlashAttribute("success", "Showtime deleted successfully!");
        return "redirect:/admin/showtimes";
    }
}
