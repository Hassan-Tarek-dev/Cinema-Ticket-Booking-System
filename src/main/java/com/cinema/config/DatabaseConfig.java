package com.cinema.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DatabaseConfig {

    private static final Logger log = LoggerFactory.getLogger(DatabaseConfig.class);

    private static DatabaseConfig instance;
    private String dbUrl;
    private String dbUsername;
    private String dbPassword;
    private int maxConnections;

    private DatabaseConfig() {
        this.dbUrl = "jdbc:h2:mem:cinemadb";
        this.dbUsername = "sa";
        this.dbPassword = "";
        this.maxConnections = 20;
        log.info("DatabaseConfig singleton instance created");
    }

    public synchronized static DatabaseConfig getInstance() {
        if (instance == null) {
            instance = new DatabaseConfig();
        }
        return instance;
    }

    public String getDbUrl() {
        return dbUrl;
    }

    public void setDbUrl(String dbUrl) {
        this.dbUrl = dbUrl;
    }

    public String getDbUsername() {
        return dbUsername;
    }

    public void setDbUsername(String dbUsername) {
        this.dbUsername = dbUsername;
    }

    public String getDbPassword() {
        return dbPassword;
    }

    public void setDbPassword(String dbPassword) {
        this.dbPassword = dbPassword;
    }

    public int getMaxConnections() {
        return maxConnections;
    }

    public void setMaxConnections(int maxConnections) {
        this.maxConnections = maxConnections;
    }

    public void displayConfig() {
        log.info("=== Database Configuration ===");
        log.info("URL: {}", dbUrl);
        log.info("Username: {}", dbUsername);
        log.info("Max Connections: {}", maxConnections);
    }
}
