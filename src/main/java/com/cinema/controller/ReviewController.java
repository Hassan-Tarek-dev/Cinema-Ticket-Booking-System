package com.cinema.controller;

import com.cinema.entity.Review;
import com.cinema.entity.Movie;
import com.cinema.entity.User;
import com.cinema.repository.ReviewRepository;
import com.cinema.repository.MovieRepository;
import com.cinema.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/reviews")
public class ReviewController {

    private static final Logger log = LoggerFactory.getLogger(ReviewController.class);

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/submit")
    public String submitReview(
            @RequestParam Long movieId,
            @RequestParam Integer rating,
            @RequestParam(required = false) String comment,
            Authentication authentication,
            RedirectAttributes redirectAttributes) {
        
        log.info("Submitting review for movie: {} by user: {}", movieId, authentication.getName());
        
        try {
            Movie movie = movieRepository.findById(movieId)
                    .orElseThrow(() -> new RuntimeException("Movie not found"));
            
            User user = userRepository.findByUsername(authentication.getName())
                    .orElseThrow(() -> new RuntimeException("User not found"));
            
            var existingReview = reviewRepository.findByMovieIdAndUserId(movieId, user.getId());
            if (!existingReview.isEmpty()) {
                Review review = existingReview.get(0);
                review.setRating(rating);
                review.setComment(comment);
                reviewRepository.save(review);
                log.info("Updated existing review");
            } else {
                Review review = new Review(movie, user, rating, comment);
                reviewRepository.save(review);
                log.info("Created new review");
            }
            
            redirectAttributes.addFlashAttribute("success", "Review submitted successfully!");
        } catch (Exception e) {
            log.error("Error submitting review", e);
            redirectAttributes.addFlashAttribute("error", "Failed to submit review");
        }
        
        return "redirect:/movies/" + movieId;
    }
}
