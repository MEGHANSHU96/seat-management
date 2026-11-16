package com.seat.management.repository;

import com.seat.management.model.Seat;
import com.seat.management.model.SeatStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SeatRepository extends JpaRepository<Seat, Long> {
    Optional<Seat> findBySeatNumber(String seatNumber);
    boolean existsBySeatNumber(String seatNumber);
    List<Seat> findByStatus(SeatStatus status);
    List<Seat> findByZoneIgnoreCase(String zone);
    List<Seat> findByFloor(Integer floor);
}
