package pl.edu.wat.backend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.edu.wat.backend.dtos.ReservationDto;
import pl.edu.wat.backend.services.ReservationService;

import java.util.List;

@RestController
public class ReservationController {
    private final ReservationService reservationService;

    @Autowired
    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping("/reservations")
    public ResponseEntity<List<ReservationDto>> getAllReservations() {
        List<ReservationDto> reservationDtos = reservationService.getReservations();
        return new ResponseEntity<>(reservationDtos, HttpStatus.OK);
    }

    @GetMapping("/reservation/{reservationId}")
    public ResponseEntity<ReservationDto> getReservation(@PathVariable("reservationId") Integer reservationId) {
        ReservationDto reservationDto = reservationService.getReservation(reservationId);
        if (reservationDto == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(reservationDto, HttpStatus.OK);
    }

    @PostMapping("/reservations")
    public ResponseEntity<Void> addReservation(@RequestBody ReservationDto reservationDto) {
        if (reservationService.addReservation(reservationDto)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PutMapping("/reservation")
    public ResponseEntity<Void> updateAccount(@RequestBody ReservationDto reservationDto) {
        reservationService.updateReservation(reservationDto);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/reservation/{reservationId}")
    public ResponseEntity<Void> deleteReservation(@PathVariable("reservationId") Integer reservationId) {
        reservationService.deleteReservation(reservationId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
