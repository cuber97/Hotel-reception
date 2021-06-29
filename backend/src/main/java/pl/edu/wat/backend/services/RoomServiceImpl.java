package pl.edu.wat.backend.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.edu.wat.backend.dtos.RoomDto;
import pl.edu.wat.backend.entities.CustomerEntity;
import pl.edu.wat.backend.entities.RoomEntity;
import pl.edu.wat.backend.repositories.RoomRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RoomServiceImpl implements RoomService {

    private final RoomRepository roomRepository;

    @Autowired
    private RoomServiceImpl(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    @Override
    public List<RoomDto> getRooms() {
        ArrayList<RoomDto> roomDtos = new ArrayList<>();

        roomRepository.findAll()
                .forEach(room -> roomDtos.add(
                        new RoomDto(
                                room.getRoomId(),
                                room.getNumber(),
                                room.getFloor(),
                                room.getMaxPeopleCapacity(),
                                room.getDailyRateForPerson(),
                                room.getPhotoFileName()
                        )
                ));

        return roomDtos;
    }

    @Override
    public boolean addRoom(RoomDto room) {
        if(roomRepository.findByNumber(room.getNumber()) != null) {
            return false;
        }

        RoomEntity roomEntity = new RoomEntity();
        roomEntity.setNumber(room.getNumber());
        roomEntity.setFloor(room.getFloor());
        roomEntity.setMaxPeopleCapacity(room.getMaxPeopleCapacity());
        roomEntity.setDailyRateForPerson(room.getDailyRateForPerson());
        roomEntity.setPhotoFileName(room.getPhotoFileName());

        roomRepository.save(roomEntity);

        return true;
    }

    @Override
    public RoomDto getRoomById(Integer id) {
        Optional<RoomEntity> roomEntity = roomRepository.findById(id);
        if(!roomEntity.isPresent()) {
            return null;
        }

        RoomEntity room = roomEntity.get();

        return new RoomDto(
                room.getRoomId(),
                room.getNumber(),
                room.getFloor(),
                room.getMaxPeopleCapacity(),
                room.getDailyRateForPerson(),
                room.getPhotoFileName()
        );
    }

    @Override
    public RoomDto getRoomByNumber(Integer number) {
        RoomEntity roomEntity = roomRepository.findByNumber(number);
        if(roomEntity == null) {
            return null;
        }

        return new RoomDto(
                roomEntity.getRoomId(),
                roomEntity.getNumber(),
                roomEntity.getFloor(),
                roomEntity.getMaxPeopleCapacity(),
                roomEntity.getDailyRateForPerson(),
                roomEntity.getPhotoFileName()
        );
    }

    @Override
    public void updateRoom(RoomDto roomDto) {
        Optional<RoomEntity> optionalCustomerEntity = roomRepository.findById(roomDto.getRoomId());
        if (optionalCustomerEntity.isPresent()) {
            RoomEntity roomEntity = optionalCustomerEntity.get();
            roomEntity.setNumber(roomDto.getNumber());
            roomEntity.setFloor(roomDto.getFloor());
            roomEntity.setMaxPeopleCapacity(roomDto.getMaxPeopleCapacity());
            roomEntity.setDailyRateForPerson(roomDto.getDailyRateForPerson());
            roomEntity.setPhotoFileName(roomDto.getPhotoFileName());
            roomRepository.save(roomEntity);
        }
    }

    @Override
    public void deleteRoomById(Integer id) {
        Optional<RoomEntity> roomEntity = roomRepository.findById(id);
        if(!roomEntity.isPresent()) {
            return;
        }
        roomRepository.delete(roomEntity.get());
    }
}
