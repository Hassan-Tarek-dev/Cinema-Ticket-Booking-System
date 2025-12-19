package com.cinema.dto;

import java.time.LocalDateTime;

public class MovieDTO {
    private Long id;
    private String title;
    private String description;
    private String genre;
    private Integer durationMinutes;
    private String posterUrl;
    private Double rating;
    private LocalDateTime releaseDate;
    private Boolean isActive;

    public MovieDTO() {}

    public MovieDTO(Long id, String title, String description, String genre, 
                    Integer durationMinutes, String posterUrl, Double rating, 
                    LocalDateTime releaseDate, Boolean isActive) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.genre = genre;
        this.durationMinutes = durationMinutes;
        this.posterUrl = posterUrl;
        this.rating = rating;
        this.releaseDate = releaseDate;
        this.isActive = isActive;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getGenre() { return genre; }
    public void setGenre(String genre) { this.genre = genre; }

    public Integer getDurationMinutes() { return durationMinutes; }
    public void setDurationMinutes(Integer durationMinutes) { this.durationMinutes = durationMinutes; }

    public String getPosterUrl() { return posterUrl; }
    public void setPosterUrl(String posterUrl) { this.posterUrl = posterUrl; }

    public Double getRating() { return rating; }
    public void setRating(Double rating) { this.rating = rating; }

    public LocalDateTime getReleaseDate() { return releaseDate; }
    public void setReleaseDate(LocalDateTime releaseDate) { this.releaseDate = releaseDate; }

    public Boolean getIsActive() { return isActive; }
    public void setIsActive(Boolean isActive) { this.isActive = isActive; }
}