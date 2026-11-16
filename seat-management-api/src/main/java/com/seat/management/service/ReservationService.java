package com.seat.management.service;

import com.seat.management.dto.ReservationRequest;
import com.seat.management.dto.ReservationResponse;
import com.seat.management.exception.InvalidOperationException;
import com.seat.management.exception.ResourceNotFoundException;
import com.seat.management.exception.SeatUnavailableException;
import com.seat.management.model.Reservation;
import com.seat.management.model.ReservationStatus;
import com.seat.management.model.Seat;
import com.seat.management.model.SeatStatus;
import com.seat.management.repository.ReservationRepository;
import com.seat.management.repository.SeatRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final SeatRepository seatRepository;

    public ReservationService(ReservationRepository reservationRepository, SeatRepository seatRepository) {
        this.reservationRepository = reservationRepository;
        this.seatRepository = seatRepository;
    }

    @Transactional
    public ReservationResponse reserveSeat(ReservationRequest request) {
        Seat seat = seatRepository.findById(request.getSeatId())
                .orElseThrow(() -> new ResourceNotFoundException("Seat not found with id: " + request.getSeatId()));

        if (seat.getStatus() != SeatStatus.AVAILABLE) {
            throw new SeatUnavailableException("Seat " + seat.getSeatNumber() + " is currently " + seat.getStatus());
        }

        if (reservationRepository.existsByStudentUsnIgnoreCaseAndStatus(request.getStudentUsn(), ReservationStatus.ACTIVE)) {
            throw new SeatUnavailableException("Student " + request.getStudentUsn() + " already has an active seat reservation");
        }

        int hours = request.getDurationHours() != null ? request.getDurationHours() : 2;
        LocalDateTime now = LocalDateTime.now();
        LocalTime startTime = now.toLocalTime();
        LocalTime endTime = startTime.plusHours(hours);

        Reservation reservation = new Reservation();
        reservation.setSeat(seat);
        reservation.setStudentName(request.getStudentName());
        reservation.setStudentEmail(request.getStudentEmail());
        reservation.setStudentUsn(request.getStudentUsn().toUpperCase());
        reservation.setReservationDate(LocalDate.now());
        reservation.setStartTime(startTime);
        reservation.setEndTime(endTime);
        reservation.setCheckInTime(now);
        reservation.setStatus(ReservationStatus.ACTIVE);

        seat.setStatus(SeatStatus.OCCUPIED);
        seatRepository.save(seat);

        return toResponse(reservationRepository.save(reservation));
    }

    public List<ReservationResponse> getAllReservations() {
        return reservationRepository.findAll().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public List<ReservationResponse> getActiveReservations() {
        return reservationRepository.findByStatus(ReservationStatus.ACTIVE).stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public ReservationResponse getReservationById(Long id) {
        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Reservation not found with id: " + id));
        return toResponse(reservation);
    }

    @Transactional
    public ReservationResponse releaseSeat(Long id) {
        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Reservation not found with id: " + id));

        if (reservation.getStatus() != ReservationStatus.ACTIVE) {
            throw new InvalidOperationException("Only ACTIVE reservations can be released");
        }

        reservation.setStatus(ReservationStatus.RELEASED);
        reservation.setReleaseTime(LocalDateTime.now());

        Seat seat = reservation.getSeat();
        seat.setStatus(SeatStatus.AVAILABLE);
        seatRepository.save(seat);

        return toResponse(reservationRepository.save(reservation));
    }

    @Transactional
    public ReservationResponse cancelReservation(Long id) {
        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Reservation not found with id: " + id));

        if (reservation.getStatus() != ReservationStatus.ACTIVE) {
            throw new InvalidOperationException("Only ACTIVE reservations can be cancelled");
        }

        reservation.setStatus(ReservationStatus.CANCELLED);
        reservation.setReleaseTime(LocalDateTime.now());

        Seat seat = reservation.getSeat();
        seat.setStatus(SeatStatus.AVAILABLE);
        seatRepository.save(seat);

        return toResponse(reservationRepository.save(reservation));
    }

    private ReservationResponse toResponse(Reservation reservation) {
        return new ReservationResponse(
                reservation.getId(),
                reservation.getSeat().getId(),
                reservation.getSeat().getSeatNumber(),
                reservation.getStudentName(),
                reservation.getStudentEmail(),
                reservation.getStudentUsn(),
                reservation.getReservationDate(),
                reservation.getStartTime(),
                reservation.getEndTime(),
                reservation.getCheckInTime(),
                reservation.getReleaseTime(),
                reservation.getStatus()
        );
    }
}
