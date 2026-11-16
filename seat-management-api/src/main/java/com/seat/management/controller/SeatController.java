package com.seat.management.controller;

import com.seat.management.dto.SeatRequest;
import com.seat.management.dto.SeatResponse;
import com.seat.management.model.SeatStatus;
import com.seat.management.service.SeatService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/seats")
public class SeatController {

    private final SeatService seatService;

    public SeatController(SeatService seatService) {
        this.seatService = seatService;
    }

    @GetMapping
    public ResponseEntity<List<SeatResponse>> getAllSeats() {
        return ResponseEntity.ok(seatService.getAllSeats());
    }

    @GetMapping("/available")
    public ResponseEntity<List<SeatResponse>> getAvailableSeats() {
        return ResponseEntity.ok(seatService.getAvailableSeats());
    }

    @GetMapping("/zone/{zone}")
    public ResponseEntity<List<SeatResponse>> getSeatsByZone(@PathVariable("zone") String zone) {
        return ResponseEntity.ok(seatService.getSeatsByZone(zone));
    }

    @GetMapping("/floor/{floor}")
    public ResponseEntity<List<SeatResponse>> getSeatsByFloor(@PathVariable("floor") Integer floor) {
        return ResponseEntity.ok(seatService.getSeatsByFloor(floor));
    }

    @GetMapping("/{id}")
    public ResponseEntity<SeatResponse> getSeatById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(seatService.getSeatById(id));
    }

    @PostMapping
    public ResponseEntity<SeatResponse> createSeat(@Valid @RequestBody SeatRequest request) {
        SeatResponse created = seatService.createSeat(request);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(created.getId())
                .toUri();
        return ResponseEntity.created(location).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SeatResponse> updateSeat(@PathVariable("id") Long id, @Valid @RequestBody SeatRequest request) {
        return ResponseEntity.ok(seatService.updateSeat(id, request));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<SeatResponse> updateSeatStatus(@PathVariable("id") Long id, @RequestParam("status") SeatStatus status) {
        return ResponseEntity.ok(seatService.updateSeatStatus(id, status));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSeat(@PathVariable("id") Long id) {
        seatService.deleteSeat(id);
        return ResponseEntity.noContent().build();
    }
}
