package com.seat.management.dto;

import com.seat.management.model.ReservationStatus;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class ReservationResponse {

    private Long id;
    private Long seatId;
    private String seatNumber;
    private String studentName;
    private String studentEmail;
    private String studentUsn;
    private LocalDate reservationDate;
    private LocalTime startTime;
    private LocalTime endTime;
    private LocalDateTime checkInTime;
    private LocalDateTime releaseTime;
    private ReservationStatus status;

    public ReservationResponse() {
    }

    public ReservationResponse(Long id, Long seatId, String seatNumber, String studentName, String studentEmail,
                               String studentUsn, LocalDate reservationDate, LocalTime startTime, LocalTime endTime,
                               LocalDateTime checkInTime, LocalDateTime releaseTime, ReservationStatus status) {
        this.id = id;
        this.seatId = seatId;
        this.seatNumber = seatNumber;
        this.studentName = studentName;
        this.studentEmail = studentEmail;
        this.studentUsn = studentUsn;
        this.reservationDate = reservationDate;
        this.startTime = startTime;
        this.endTime = endTime;
        this.checkInTime = checkInTime;
        this.releaseTime = releaseTime;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSeatId() {
        return seatId;
    }

    public void setSeatId(Long seatId) {
        this.seatId = seatId;
    }

    public String getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(String seatNumber) {
        this.seatNumber = seatNumber;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getStudentEmail() {
        return studentEmail;
    }

    public void setStudentEmail(String studentEmail) {
        this.studentEmail = studentEmail;
    }

    public String getStudentUsn() {
        return studentUsn;
    }

    public void setStudentUsn(String studentUsn) {
        this.studentUsn = studentUsn;
    }

    public LocalDate getReservationDate() {
        return reservationDate;
    }

    public void setReservationDate(LocalDate reservationDate) {
        this.reservationDate = reservationDate;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    public LocalDateTime getCheckInTime() {
        return checkInTime;
    }

    public void setCheckInTime(LocalDateTime checkInTime) {
        this.checkInTime = checkInTime;
    }

    public LocalDateTime getReleaseTime() {
        return releaseTime;
    }

    public void setReleaseTime(LocalDateTime releaseTime) {
        this.releaseTime = releaseTime;
    }

    public ReservationStatus getStatus() {
        return status;
    }

    public void setStatus(ReservationStatus status) {
        this.status = status;
    }
}
