package com.cinema.config;

import java.time.LocalDateTime;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.cinema.entity.Movie;
import com.cinema.entity.User;
import com.cinema.repository.MovieRepository;
import com.cinema.repository.UserRepository;

@Configuration
public class DataInitializer {
    
    @Bean
    public CommandLineRunner initData(
            MovieRepository movieRepo, 
            UserRepository userRepo,
            PasswordEncoder passwordEncoder) {
        return args -> {
            if (userRepo.findByUsername("admin").isEmpty()) {
                User admin = new User();
                admin.setUsername("admin");
                admin.setPassword(passwordEncoder.encode("admin123"));
                admin.setEmail("admin@cinema.com");
                admin.setFullName("System Administrator");
                admin.setRole(User.UserRole.ADMIN);
                admin.setEnabled(true);
                userRepo.save(admin);
                System.out.println("✓ Admin user created: admin/admin123");
            }

            if (movieRepo.count() == 0) {
                Movie m1 = new Movie();
                m1.setTitle("Inception");
                m1.setGenre("Sci-Fi");
                m1.setRating(8.8);
                m1.setDurationMinutes(148);
                m1.setDescription("A skilled thief is given a chance at redemption if he can complete an impossible task: Inception.");
                m1.setPosterUrl("https://via.placeholder.com/300");
                m1.setReleaseDate(LocalDateTime.now().minusMonths(2));
                m1.setIsActive(true);
                movieRepo.save(m1);

                Movie m2 = new Movie();
                m2.setTitle("The Dark Knight");
                m2.setGenre("Action");
                m2.setRating(9.0);
                m2.setDurationMinutes(152);
                m2.setDescription("Batman faces the Joker in a battle for Gotham's soul.");
                m2.setPosterUrl("https://via.placeholder.com/300");
                m2.setReleaseDate(LocalDateTime.now().minusMonths(1));
                m2.setIsActive(true);
                movieRepo.save(m2);

                Movie m3 = new Movie();
                m3.setTitle("The Shawshank Redemption");
                m3.setGenre("Drama");
                m3.setRating(9.3);
                m3.setDurationMinutes(142);
                m3.setDescription("Two imprisoned men bond over a number of years, finding solace and eventual redemption.");
                m3.setPosterUrl("https://via.placeholder.com/300");
                m3.setReleaseDate(LocalDateTime.now().minusWeeks(2));
                m3.setIsActive(true);
                movieRepo.save(m3);

                Movie m4 = new Movie();
                m4.setTitle("Pulp Fiction");
                m4.setGenre("Action");
                m4.setRating(8.9);
                m4.setDurationMinutes(154);
                m4.setDescription("The lives of two mob hitmen, a boxer, and a pair of diner bandits intertwine.");
                m4.setPosterUrl("https://via.placeholder.com/300");
                m4.setReleaseDate(LocalDateTime.now().minusWeeks(1));
                m4.setIsActive(true);
                movieRepo.save(m4);

                Movie m5 = new Movie();
                m5.setTitle("The Hangover");
                m5.setGenre("Comedy");
                m5.setRating(7.7);
                m5.setDurationMinutes(100);
                m5.setDescription("Three buddies wake up from a bachelor party in Las Vegas with no memory of the previous night.");
                m5.setPosterUrl("https://via.placeholder.com/300");
                m5.setReleaseDate(LocalDateTime.now());
                m5.setIsActive(true);
                movieRepo.save(m5);

                System.out.println("✓ Sample movies created");
            }
        };
    }
}