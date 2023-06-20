package pl.edu.wat.backend.services;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.edu.wat.backend.dtos.CustomerDto;
import pl.edu.wat.backend.dtos.ReservationDto;
import pl.edu.wat.backend.dtos.RoomDto;
import pl.edu.wat.backend.entities.CustomerEntity;
import pl.edu.wat.backend.entities.ReservationEntity;
import pl.edu.wat.backend.entities.RoomEntity;
import pl.edu.wat.backend.exceptions.CurrencyRateNotAvailableException;
import pl.edu.wat.backend.repositories.ReservationRepository;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ReservationServiceImpl implements ReservationService {
    private final ReservationRepository reservationRepository;
    private final ModelMapper modelMapper;
    private final CurrencyService currencyService;

    @Autowired
    public ReservationServiceImpl(ReservationRepository reservationRepository, ModelMapper modelMapper, CurrencyService currencyService) {
        this.reservationRepository = reservationRepository;
        this.modelMapper = modelMapper;
        this.currencyService = currencyService;
    }

    @Override
    public List<ReservationDto> getReservations() {
        List<ReservationDto> reservationDtos = new ArrayList<>();
        reservationRepository
                .findAll()
                .forEach(reservation -> {
                    ReservationDto reservationDto = this.modelMapper.map(reservation, ReservationDto.class);
                    reservationDto.setCustomer(this.modelMapper.map(reservation.getCustomerEntity(), CustomerDto.class));
                    reservationDto.setRoom(this.modelMapper.map(reservation.getRoomEntity(), RoomDto.class));
                    reservationDtos.add(reservationDto);
                });
        return reservationDtos;
    }

    @Override
    public boolean addReservation(ReservationDto reservationDto) {
        System.out.println(reservationDto);
        ReservationEntity reservationEntity = this.modelMapper.map(reservationDto, ReservationEntity.class);
        if (reservationEntity.getCurrency().equals("EUR")) {
            try {
                BigDecimal rate = this.currencyService.getPlnToEuroCurrency();
                BigDecimal price = reservationEntity.getPrice().multiply(rate);
                price = price.setScale(2, RoundingMode.FLOOR);
                reservationEntity.setPrice(price);
            }
            catch (CurrencyRateNotAvailableException exception) {
                return false;
            }
        }
        reservationEntity.setCustomerEntity(this.modelMapper.map(reservationDto.getCustomer(), CustomerEntity.class));
        reservationEntity.setRoomEntity(this.modelMapper.map(reservationDto.getRoom(), RoomEntity.class));
        reservationRepository.save(reservationEntity);
        return true;
    }

    @Override
    public ReservationDto getReservation(Integer id) {
        Optional<ReservationEntity> optionalReservationEntity = reservationRepository.findById(id);
        if(!optionalReservationEntity.isPresent()) {
            return null;
        }
        ReservationEntity reservationEntity = optionalReservationEntity.get();
        ReservationDto reservationDto = this.modelMapper.map(reservationEntity, ReservationDto.class);
        reservationDto.setCustomer(this.modelMapper.map(reservationEntity.getCustomerEntity(), CustomerDto.class));
        reservationDto.setRoom(this.modelMapper.map(reservationEntity.getRoomEntity(), RoomDto.class));
        return reservationDto;
    }

    @Override
    public void deleteReservation(Integer id) {
        reservationRepository.deleteById(id);
    }

    @Override
    public void updateReservation(ReservationDto reservationDto) {
        Optional<ReservationEntity> optionalReservationEntity = reservationRepository.findById(reservationDto.getReservationId());
        if (optionalReservationEntity.isPresent()) {
            ReservationEntity reservationEntity = this.modelMapper.map(reservationDto, ReservationEntity.class);
            reservationEntity.setCustomerEntity(this.modelMapper.map(reservationDto.getCustomer(), CustomerEntity.class));
            reservationEntity.setRoomEntity(this.modelMapper.map(reservationDto.getRoom(), RoomEntity.class));
            reservationEntity.setReservationId(optionalReservationEntity.get().getReservationId());
            reservationRepository.save(reservationEntity);
        }
    }
}
