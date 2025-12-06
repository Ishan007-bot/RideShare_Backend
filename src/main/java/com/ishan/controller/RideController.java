package com.ishan.controller;

import com.ishan.dto.CreateRideRequest;
import com.ishan.model.Ride;
import com.ishan.service.RideService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class RideController {

    private final RideService rideService;

    public RideController(RideService rideService) {
        this.rideService = rideService;
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping("/rides")
    public ResponseEntity<Ride> requestRide(@Valid @RequestBody CreateRideRequest request) {
        Ride newRide = rideService.requestRide(request);
        return ResponseEntity.status(201).body(newRide);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/user/rides")
    public ResponseEntity<List<Ride>> viewMyRides() {
        List<Ride> rides = rideService.viewUserRides();
        return ResponseEntity.ok(rides);
    }

    @PreAuthorize("hasRole('ROLE_DRIVER')")
    @GetMapping("/driver/rides/requests")
    public ResponseEntity<List<Ride>> viewPendingRequests() {
        List<Ride> pendingRides = rideService.viewPendingRideRequests();
        return ResponseEntity.ok(pendingRides);
    }

    @PreAuthorize("hasRole('ROLE_DRIVER')")
    @PostMapping("/driver/rides/{rideId}/accept")
    public ResponseEntity<Ride> acceptRide(@PathVariable String rideId) {
        Ride acceptedRide = rideService.acceptRide(rideId);
        return ResponseEntity.ok(acceptedRide);
    }

    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_DRIVER')")
    @PostMapping("/rides/{rideId}/complete")
    public ResponseEntity<Ride> completeRide(@PathVariable String rideId) {
        Ride completedRide = rideService.completeRide(rideId);
        return ResponseEntity.ok(completedRide);
    }
}