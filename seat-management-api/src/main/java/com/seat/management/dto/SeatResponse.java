package com.seat.management.dto;

import com.seat.management.model.SeatStatus;

public class SeatResponse {

    private Long id;
    private String seatNumber;
    private String zone;
    private Integer floor;
    private Integer capacity;
    private Boolean hasPowerOutlet;
    private SeatStatus status;

    public SeatResponse() {
    }

    public SeatResponse(Long id, String seatNumber, String zone, Integer floor, Integer capacity, Boolean hasPowerOutlet, SeatStatus status) {
        this.id = id;
        this.seatNumber = seatNumber;
        this.zone = zone;
        this.floor = floor;
        this.capacity = capacity;
        this.hasPowerOutlet = hasPowerOutlet;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public SeatStatus getStatus() {
        return status;
    }

    public void setStatus(SeatStatus status) {
        this.status = status;
    }
}
