package com.cinema.dto;

public class UserDTO {
    private Long id;
    private String email;
    private String username;
    private String fullName;
    private String role;
    private Boolean enabled;

    public UserDTO() {}


    public UserDTO(Long id, String email, String username, String fullName, String role, Boolean enabled) {
        this.id = id;
        this.email = email;
        this.username = username;
        this.fullName = fullName;
        this.role = role;
        this.enabled = enabled;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public Boolean getEnabled() { return enabled; }
    public void setEnabled(Boolean enabled) { this.enabled = enabled; }
}