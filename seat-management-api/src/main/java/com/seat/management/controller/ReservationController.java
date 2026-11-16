package com.seat.management.controller;

import com.seat.management.dto.ReservationRequest;
import com.seat.management.dto.ReservationResponse;
import com.seat.management.service.ReservationService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reservations")
public class ReservationController {

    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @PostMapping
    public ResponseEntity<ReservationResponse> reserveSeat(@Valid @RequestBody ReservationRequest request) {
        return new ResponseEntity<>(reservationService.reserveSeat(request), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<ReservationResponse>> getAllReservations() {
        return ResponseEntity.ok(reservationService.getAllReservations());
    }

    @GetMapping("/active")
    public ResponseEntity<List<ReservationResponse>> getActiveReservations() {
        return ResponseEntity.ok(reservationService.getActiveReservations());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReservationResponse> getReservationById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(reservationService.getReservationById(id));
    }

    @PutMapping("/{id}/release")
    public ResponseEntity<ReservationResponse> releaseSeat(@PathVariable("id") Long id) {
        return ResponseEntity.ok(reservationService.releaseSeat(id));
    }

    @PutMapping("/{id}/cancel")
    public ResponseEntity<ReservationResponse> cancelReservation(@PathVariable("id") Long id) {
        return ResponseEntity.ok(reservationService.cancelReservation(id));
    }
}
