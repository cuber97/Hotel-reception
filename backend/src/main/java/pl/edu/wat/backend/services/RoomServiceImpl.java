package pl.edu.wat.backend.services;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.edu.wat.backend.dtos.RoomDto;
import pl.edu.wat.backend.entities.RoomEntity;
import pl.edu.wat.backend.repositories.RoomRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RoomServiceImpl implements RoomService {

    private final RoomRepository roomRepository;
    private final ModelMapper modelMapper;

    @Autowired
    private RoomServiceImpl(RoomRepository roomRepository, ModelMapper modelMapper) {
        this.roomRepository = roomRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<RoomDto> getRooms() {
        ArrayList<RoomDto> roomDtos = new ArrayList<>();

        roomRepository.findAll()
                .forEach(room -> roomDtos.add(
                        this.modelMapper.map(room, RoomDto.class)
                ));

        return roomDtos;
    }

    @Override
    public boolean addRoom(RoomDto room) {
        if(roomRepository.findByNumber(room.getNumber()) != null) {
            return false;
        }

        RoomEntity roomEntity = this.modelMapper.map(room, RoomEntity.class);

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

        return this.modelMapper.map(room, RoomDto.class);
    }

    @Override
    public RoomDto getRoomByNumber(Integer number) {
        RoomEntity roomEntity = roomRepository.findByNumber(number);
        if(roomEntity == null) {
            return null;
        }

        return this.modelMapper.map(roomEntity, RoomDto.class);
    }

    @Override
    public void updateRoom(RoomDto roomDto) {
        Optional<RoomEntity> optionalCustomerEntity = roomRepository.findById(roomDto.getRoomId());
        if (optionalCustomerEntity.isPresent()) {
            RoomEntity roomEntity = this.modelMapper.map(roomDto, RoomEntity.class);
            roomEntity.setRoomId(optionalCustomerEntity.get().getRoomId());
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
