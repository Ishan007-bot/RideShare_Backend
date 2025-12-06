package com.ishan.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.Date;

@Document(collection = "rides")
public class Ride {
    @Id
    private String id;
    private String userId; // Passenger ID (FK)
    private String driverId; // Driver ID (FK), nullable
    private String pickupLocation;
    private String dropLocation;
    private String status; // REQUESTED, ACCEPTED, COMPLETED
    private Date createdAt = new Date(); // Set default on creation

    // Static Status Constants
    public static final String STATUS_REQUESTED = "REQUESTED";
    public static final String STATUS_ACCEPTED = "ACCEPTED";
    public static final String STATUS_COMPLETED = "COMPLETED";

    // Required Constructor
    public Ride() {}

    // Getters
    public String getId() { return id; }
    public String getUserId() { return userId; }
    public String getDriverId() { return driverId; }
    public String getPickupLocation() { return pickupLocation; }
    public String getDropLocation() { return dropLocation; }
    public String getStatus() { return status; }
    public Date getCreatedAt() { return createdAt; }

    // Setters
    public void setId(String id) { this.id = id; }
    public void setUserId(String userId) { this.userId = userId; }
    public void setDriverId(String driverId) { this.driverId = driverId; }
    public void setPickupLocation(String pickupLocation) { this.pickupLocation = pickupLocation; }
    public void setDropLocation(String dropLocation) { this.dropLocation = dropLocation; }
    public void setStatus(String status) { this.status = status; }
    public void setCreatedAt(Date createdAt) { this.createdAt = createdAt; }
}