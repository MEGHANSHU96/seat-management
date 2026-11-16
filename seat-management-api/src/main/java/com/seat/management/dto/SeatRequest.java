package com.seat.management.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class SeatRequest {

    @NotBlank(message = "Seat number is required")
    private String seatNumber;

    @NotBlank(message = "Zone is required")
    private String zone;

    @NotNull(message = "Floor is required")
    @Min(value = 0, message = "Floor must be 0 or higher")
    @Max(value = 20, message = "Floor must be 20 or lower")
    private Integer floor;

    @NotNull(message = "Capacity is required")
    @Min(value = 1, message = "Capacity must be at least 1")
    @Max(value = 12, message = "Capacity cannot exceed 12")
    private Integer capacity;

    @NotNull(message = "Power outlet availability is required")
    private Boolean hasPowerOutlet;

    public SeatRequest() {
    }

    public SeatRequest(String seatNumber, String zone, Integer floor, Integer capacity, Boolean hasPowerOutlet) {
        this.seatNumber = seatNumber;
        this.zone = zone;
        this.floor = floor;
        this.capacity = capacity;
        this.hasPowerOutlet = hasPowerOutlet;
    }

    public String getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(String seatNumber) {
        this.seatNumber = seatNumber;
    }

    public String getZone() {
        return zone;
    }

    public void setZone(String zone) {
        this.zone = zone;
    }

    public Integer getFloor() {
        return floor;
    }

    public void setFloor(Integer floor) {
        this.floor = floor;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public Boolean getHasPowerOutlet() {
        return hasPowerOutlet;
    }

    public void setHasPowerOutlet(Boolean hasPowerOutlet) {
        this.hasPowerOutlet = hasPowerOutlet;
    }
}
