package com.ishan.exception;

import java.time.LocalDateTime;

public class ApiError {
    private String error;
    private String message;
    private LocalDateTime timestamp;

    public ApiError(String error, String message) {
        this.error = error;
        this.message = message;
        this.timestamp = LocalDateTime.now();
    }

    // Getters
    public String getError() { return error; }
    public String getMessage() { return message; }
    public LocalDateTime getTimestamp() { return timestamp; }

    // Setters
    public void setError(String error) { this.error = error; }
    public void setMessage(String message) { this.message = message; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }
}
