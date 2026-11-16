package com.seat.management.repository;

import com.seat.management.model.Reservation;
import com.seat.management.model.ReservationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    List<Reservation> findByStatus(ReservationStatus status);
    List<Reservation> findByStudentUsnIgnoreCase(String studentUsn);
    boolean existsByStudentUsnIgnoreCaseAndStatus(String studentUsn, ReservationStatus status);
}
