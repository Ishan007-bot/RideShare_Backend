package com.ishan.dto;

import jakarta.validation.constraints.NotBlank;

public class CreateRideRequest {
    @NotBlank(message = "Pickup is required")
    private String pickupLocation;

    @NotBlank(message = "Drop is required")
    private String dropLocation;

    // Required Constructor
    public CreateRideRequest() {}

    // Getters
    public String getPickupLocation() { return pickupLocation; }
    public String getDropLocation() { return dropLocation; }

    // Setters
    public void setPickupLocation(String pickupLocation) { this.pickupLocation = pickupLocation; }
    public void setDropLocation(String dropLocation) { this.dropLocation = dropLocation; }
}