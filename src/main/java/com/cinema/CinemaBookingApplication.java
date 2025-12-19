package com.cinema;

import com.cinema.config.DatabaseConfig;
import com.cinema.service.BookingFacade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class CinemaBookingApplication {

    private static final Logger log = LoggerFactory.getLogger(CinemaBookingApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(CinemaBookingApplication.class, args);
        DatabaseConfig dbConfig = DatabaseConfig.getInstance();
        dbConfig.displayConfig();
    }

    @Bean
    public CommandLineRunner initializeProject(BookingFacade bookingFacade) {
        return args -> {
            log.info("=== Initializing Design Patterns ===");
            bookingFacade.initializeObservers();
        };
    }
}