package com.ishan.service;

import com.ishan.dto.CreateRideRequest;
import com.ishan.exception.BadRequestException;
import com.ishan.exception.NotFoundException;
import com.ishan.model.Ride;
import com.ishan.repository.RideRepository;
import com.ishan.util.SecurityUtil;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RideService {

    private final RideRepository rideRepository;
    private final SecurityUtil securityUtil;

    public RideService(RideRepository rideRepository, SecurityUtil securityUtil) {
        this.rideRepository = rideRepository;
        this.securityUtil = securityUtil;
    }

    public Ride requestRide(CreateRideRequest request) {
        String userId = securityUtil.getCurrentUsername()
                .orElseThrow(() -> new BadRequestException("User must be logged in to request a ride."));

        if (!securityUtil.hasRole("ROLE_USER")) {
            throw new BadRequestException("Only users with ROLE_USER can request a ride.");
        }

        Ride ride = new Ride();
        ride.setUserId(userId);
        ride.setPickupLocation(request.getPickupLocation());
        ride.setDropLocation(request.getDropLocation());
        ride.setStatus(Ride.STATUS_REQUESTED);

        return rideRepository.save(ride);
    }

    public List<Ride> viewPendingRideRequests() {
        return rideRepository.findByStatus(Ride.STATUS_REQUESTED);
    }

    public Ride acceptRide(String rideId) {
        String driverId = securityUtil.getCurrentUsername()
                .orElseThrow(() -> new BadRequestException("Driver must be logged in."));

        Ride ride = rideRepository.findById(rideId)
                .orElseThrow(() -> new NotFoundException("Ride not found with ID: " + rideId));

        if (!Ride.STATUS_REQUESTED.equals(ride.getStatus())) {
            throw new BadRequestException("Ride is not in REQUESTED status.");
        }

        ride.setDriverId(driverId);
        ride.setStatus(Ride.STATUS_ACCEPTED);

        return rideRepository.save(ride);
    }

    public Ride completeRide(String rideId) {
        Ride ride = rideRepository.findById(rideId)
                .orElseThrow(() -> new NotFoundException("Ride not found with ID: " + rideId));

        if (!Ride.STATUS_ACCEPTED.equals(ride.getStatus())) {
            throw new BadRequestException("Ride must be ACCEPTED to be completed.");
        }

        String currentUsername = securityUtil.getCurrentUsername()
                .orElseThrow(() -> new BadRequestException("User must be logged in."));

        String userId = ride.getUserId();
        String driverId = ride.getDriverId();
        if (!currentUsername.equals(userId) && !currentUsername.equals(driverId)) {
            throw new BadRequestException("Only the passenger or the assigned driver can complete this ride.");
        }

        ride.setStatus(Ride.STATUS_COMPLETED);

        return rideRepository.save(ride);
    }

    public List<Ride> viewUserRides() {
        String userId = securityUtil.getCurrentUsername()
                .orElseThrow(() -> new BadRequestException("User must be logged in."));

        return rideRepository.findByUserId(userId);
    }
}
