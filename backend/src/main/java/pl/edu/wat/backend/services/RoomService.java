package pl.edu.wat.backend.services;

import org.springframework.stereotype.Service;
import pl.edu.wat.backend.dtos.CustomerDto;
import pl.edu.wat.backend.dtos.RoomDto;

import java.util.List;

@Service
public interface RoomService {
    List<RoomDto> getRooms();

    boolean addRoom(RoomDto room);

    RoomDto getRoomById(Integer id);

    RoomDto getRoomByNumber(Integer number);

    void updateRoom(RoomDto roomDto);

    void deleteRoomById(Integer id);
}
