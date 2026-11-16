package com.seat.management.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class ReservationRequest {

    @NotNull(message = "Seat ID is required")
    private Long seatId;

    @NotBlank(message = "Student name is required")
    private String studentName;

    @NotBlank(message = "Student email is required")
    @Email(message = "Student email must be valid")
    private String studentEmail;

    @NotBlank(message = "Student USN is required")
    private String studentUsn;

    @Min(value = 1, message = "Duration hours must be at least 1")
    private Integer durationHours = 2;

    public ReservationRequest() {
    }

    public ReservationRequest(Long seatId, String studentName, String studentEmail, String studentUsn, Integer durationHours) {
        this.seatId = seatId;
        this.studentName = studentName;
        this.studentEmail = studentEmail;
        this.studentUsn = studentUsn;
        this.durationHours = durationHours;
    }

    public Long getSeatId() {
        return seatId;
    }

    public void setSeatId(Long seatId) {
        this.seatId = seatId;
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

    public Integer getDurationHours() {
        return durationHours;
    }

    public void setDurationHours(Integer durationHours) {
        this.durationHours = durationHours;
    }
}
