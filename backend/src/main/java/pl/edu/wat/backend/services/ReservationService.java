package pl.edu.wat.backend.services;

import org.springframework.stereotype.Service;
import pl.edu.wat.backend.dtos.ReservationDto;
import pl.edu.wat.backend.dtos.RoomDto;

import java.util.ArrayList;
import java.util.List;

@Service
public interface ReservationService {
    List<ReservationDto> getReservations();

    boolean addReservation(ReservationDto reservationDto);

    ReservationDto getReservation(Integer id);

    void deleteReservation(Integer id);

    void updateReservation(ReservationDto reservationDto);
}
