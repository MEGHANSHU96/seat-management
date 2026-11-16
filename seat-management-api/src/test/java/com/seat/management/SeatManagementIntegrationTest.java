package com.seat.management;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.seat.management.dto.ReservationRequest;
import com.seat.management.dto.SeatRequest;
import com.seat.management.model.Seat;
import com.seat.management.model.SeatStatus;
import com.seat.management.repository.ReservationRepository;
import com.seat.management.repository.SeatRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class SeatManagementIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private SeatRepository seatRepository;

    @Autowired
    private ReservationRepository reservationRepository;

    @BeforeEach
    void setUp() {
        reservationRepository.deleteAll();
        seatRepository.deleteAll();
    }

    @Test
    void testCreateSeatSuccess() throws Exception {
        SeatRequest request = new SeatRequest("A-101", "Silent Study", 1, 1, true);

        mockMvc.perform(post("/seats")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"))
                .andExpect(jsonPath("$.seatNumber").value("A-101"))
                .andExpect(jsonPath("$.status").value("AVAILABLE"));
    }

    @Test
    void testCreateDuplicateSeatConflict() throws Exception {
        seatRepository.save(new Seat("A-101", "Silent Study", 1, 1, true, SeatStatus.AVAILABLE));
        SeatRequest duplicate = new SeatRequest("A-101", "Reading Hall", 2, 1, false);

        mockMvc.perform(post("/seats")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(duplicate)))
                .andExpect(status().isConflict());
    }

    @Test
    void testCreateSeatValidationFailure() throws Exception {
        SeatRequest invalid = new SeatRequest("", "", -1, 0, null);

        mockMvc.perform(post("/seats")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalid)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors").isMap());
    }

    @Test
    void testGetAvailableSeats() throws Exception {
        seatRepository.save(new Seat("A-101", "Silent Study", 1, 1, true, SeatStatus.AVAILABLE));
        seatRepository.save(new Seat("A-102", "Silent Study", 1, 1, true, SeatStatus.OCCUPIED));

        mockMvc.perform(get("/seats/available"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].seatNumber").value("A-101"));
    }

    @Test
    void testFilterSeatsByZoneAndFloor() throws Exception {
        seatRepository.save(new Seat("B-201", "Computer Lab", 2, 1, true, SeatStatus.AVAILABLE));
        seatRepository.save(new Seat("C-301", "Reading Hall", 3, 2, false, SeatStatus.AVAILABLE));

        mockMvc.perform(get("/seats/zone/Computer Lab"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].seatNumber").value("B-201"));

        mockMvc.perform(get("/seats/floor/3"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].seatNumber").value("C-301"));
    }

    @Test
    void testUpdateAndDeleteSeat() throws Exception {
        Seat saved = seatRepository.save(new Seat("D-101", "Reading Hall", 1, 1, false, SeatStatus.AVAILABLE));
        SeatRequest update = new SeatRequest("D-101-UP", "Silent Study", 1, 1, true);

        mockMvc.perform(put("/seats/" + saved.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(update)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.seatNumber").value("D-101-UP"));

        mockMvc.perform(delete("/seats/" + saved.getId()))
                .andExpect(status().isNoContent());
    }

    @Test
    void testReserveSeatSuccess() throws Exception {
        Seat seat = seatRepository.save(new Seat("S-01", "Silent Study", 1, 1, true, SeatStatus.AVAILABLE));
        ReservationRequest req = new ReservationRequest(seat.getId(), "Meghanshu Kumar", "meghanshu@msrit.edu", "1MS24CY035", 3);

        mockMvc.perform(post("/reservations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.seatNumber").value("S-01"))
                .andExpect(jsonPath("$.studentUsn").value("1MS24CY035"))
                .andExpect(jsonPath("$.status").value("ACTIVE"));
    }

    @Test
    void testReserveOccupiedSeatConflict() throws Exception {
        Seat seat = seatRepository.save(new Seat("S-02", "Silent Study", 1, 1, true, SeatStatus.OCCUPIED));
        ReservationRequest req = new ReservationRequest(seat.getId(), "Meghanshu Kumar", "meghanshu@msrit.edu", "1MS24CY035", 2);

        mockMvc.perform(post("/reservations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isConflict());
    }

    @Test
    void testReleaseSeatMakesItAvailableAgain() throws Exception {
        Seat seat = seatRepository.save(new Seat("S-03", "Computer Lab", 2, 1, true, SeatStatus.AVAILABLE));
        ReservationRequest req = new ReservationRequest(seat.getId(), "Meghanshu Kumar", "meghanshu@msrit.edu", "1MS24CY035", 2);

        String responseJson = mockMvc.perform(post("/reservations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();

        Long reservationId = objectMapper.readTree(responseJson).get("id").asLong();

        mockMvc.perform(put("/reservations/" + reservationId + "/release"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("RELEASED"));

        mockMvc.perform(get("/seats/" + seat.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("AVAILABLE"));
    }

    @Test
    void testActuatorHealthEndpoint() throws Exception {
        mockMvc.perform(get("/actuator/health"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("UP"));
    }
}
