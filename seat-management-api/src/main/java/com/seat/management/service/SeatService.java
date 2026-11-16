package com.seat.management.service;

import com.seat.management.dto.SeatRequest;
import com.seat.management.dto.SeatResponse;
import com.seat.management.exception.DuplicateResourceException;
import com.seat.management.exception.InvalidOperationException;
import com.seat.management.exception.ResourceNotFoundException;
import com.seat.management.model.Seat;
import com.seat.management.model.SeatStatus;
import com.seat.management.repository.SeatRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SeatService {

    private final SeatRepository seatRepository;

    public SeatService(SeatRepository seatRepository) {
        this.seatRepository = seatRepository;
    }

    public List<SeatResponse> getAllSeats() {
        return seatRepository.findAll().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public List<SeatResponse> getAvailableSeats() {
        return seatRepository.findByStatus(SeatStatus.AVAILABLE).stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public List<SeatResponse> getSeatsByZone(String zone) {
        return seatRepository.findByZoneIgnoreCase(zone).stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public List<SeatResponse> getSeatsByFloor(Integer floor) {
        return seatRepository.findByFloor(floor).stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public SeatResponse getSeatById(Long id) {
        Seat seat = findSeatEntity(id);
        return toResponse(seat);
    }

    @Transactional
    public SeatResponse createSeat(SeatRequest request) {
        if (seatRepository.existsBySeatNumber(request.getSeatNumber())) {
            throw new DuplicateResourceException("Seat with number " + request.getSeatNumber() + " already exists");
        }
        Seat seat = new Seat(
                request.getSeatNumber(),
                request.getZone(),
                request.getFloor(),
                request.getCapacity(),
                request.getHasPowerOutlet(),
                SeatStatus.AVAILABLE
        );
        return toResponse(seatRepository.save(seat));
    }

    @Transactional
    public SeatResponse updateSeat(Long id, SeatRequest request) {
        Seat seat = findSeatEntity(id);
        if (!seat.getSeatNumber().equals(request.getSeatNumber()) && seatRepository.existsBySeatNumber(request.getSeatNumber())) {
            throw new DuplicateResourceException("Seat with number " + request.getSeatNumber() + " already exists");
        }
        seat.setSeatNumber(request.getSeatNumber());
        seat.setZone(request.getZone());
        seat.setFloor(request.getFloor());
        seat.setCapacity(request.getCapacity());
        seat.setHasPowerOutlet(request.getHasPowerOutlet());
        return toResponse(seatRepository.save(seat));
    }

    @Transactional
    public SeatResponse updateSeatStatus(Long id, SeatStatus status) {
        Seat seat = findSeatEntity(id);
        seat.setStatus(status);
        return toResponse(seatRepository.save(seat));
    }

    @Transactional
    public void deleteSeat(Long id) {
        Seat seat = findSeatEntity(id);
        if (seat.getStatus() == SeatStatus.OCCUPIED) {
            throw new InvalidOperationException("Cannot delete an occupied seat");
        }
        seatRepository.delete(seat);
    }

    public Seat findSeatEntity(Long id) {
        return seatRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Seat not found with id: " + id));
    }

    public SeatResponse toResponse(Seat seat) {
        return new SeatResponse(
                seat.getId(),
                seat.getSeatNumber(),
                seat.getZone(),
                seat.getFloor(),
                seat.getCapacity(),
                seat.getHasPowerOutlet(),
                seat.getStatus()
        );
    }
}
