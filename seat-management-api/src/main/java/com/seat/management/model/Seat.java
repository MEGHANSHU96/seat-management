package com.seat.management.model;

import jakarta.persistence.*;

@Entity
@Table(name = "seats")
public class Seat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String seatNumber;

    @Column(nullable = false)
    private String zone;

    @Column(nullable = false)
    private Integer floor;

    @Column(nullable = false)
    private Integer capacity;

    @Column(nullable = false)
    private Boolean hasPowerOutlet;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SeatStatus status;

    public Seat() {
    }

    public Seat(String seatNumber, String zone, Integer floor, Integer capacity, Boolean hasPowerOutlet, SeatStatus status) {
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
