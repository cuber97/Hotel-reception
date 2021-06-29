package pl.edu.wat.backend.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.edu.wat.backend.dtos.CustomerDto;
import pl.edu.wat.backend.dtos.ReservationDto;
import pl.edu.wat.backend.dtos.RoomDto;
import pl.edu.wat.backend.entities.CustomerEntity;
import pl.edu.wat.backend.entities.ReservationEntity;
import pl.edu.wat.backend.entities.RoomEntity;
import pl.edu.wat.backend.repositories.ReservationRepository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ReservationServiceImpl implements ReservationService {
    private final ReservationRepository reservationRepository;

    @Autowired
    public ReservationServiceImpl(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    @Override
    public List<ReservationDto> getReservations() {
        List<ReservationDto> reservationDtos = new ArrayList<>();
        reservationRepository.findAll()
                .forEach(reservation -> reservationDtos.add(new ReservationDto(reservation.getReservationId(), reservation.getDateFrom(), reservation.getDateTo(), reservation.getCurrency(),
                        reservation.getPrice(), reservation.getGuestsInRoom(),
                        new CustomerDto(reservation.getCustomerEntity().getCustomerId(), reservation.getCustomerEntity().getFirstName(),
                                reservation.getCustomerEntity().getLastName(), reservation.getCustomerEntity().getEmail(),
                                reservation.getCustomerEntity().getPesel(), reservation.getCustomerEntity().getCreditCardNumber(),
                                reservation.getCustomerEntity().getPhoneNumber()),
                        new RoomDto(reservation.getRoomEntity().getRoomId(),
                                reservation.getRoomEntity().getNumber(),
                                reservation.getRoomEntity().getFloor(),
                                reservation.getRoomEntity().getMaxPeopleCapacity(),
                                reservation.getRoomEntity().getDailyRateForPerson(),
                                reservation.getRoomEntity().getPhotoFileName()))));
        return reservationDtos;
    }

    @Override
    public boolean addReservation(ReservationDto reservationDto) {
        System.out.println(reservationDto);
        ReservationEntity reservationEntity = new ReservationEntity();
        reservationEntity.setReservationId(reservationDto.getReservationId());
        reservationEntity.setDateFrom(reservationDto.getDateFrom());
        reservationEntity.setDateTo(reservationDto.getDateTo());
        reservationEntity.setCurrency(reservationDto.getCurrency());
        reservationEntity.setGuestsInRoom(reservationDto.getGuestsInRoom());
        reservationEntity.setPrice(reservationDto.getRoom().getDailyRateForPerson().multiply(new BigDecimal(reservationDto.getGuestsInRoom())));
        reservationEntity.setCustomerEntity(new CustomerEntity(
                reservationDto.getCustomer().getCustomerId(),
                reservationDto.getCustomer().getFirstName(),
                reservationDto.getCustomer().getLastName(),
                reservationDto.getCustomer().getEmail(),
                reservationDto.getCustomer().getPesel(),
                reservationDto.getCustomer().getCreditCardNumber(),
                reservationDto.getCustomer().getPhoneNumber()));
        reservationEntity.setRoomEntity(new RoomEntity(
                reservationDto.getRoom().getRoomId(),
                reservationDto.getRoom().getNumber(),
                reservationDto.getRoom().getFloor(),
                reservationDto.getRoom().getMaxPeopleCapacity(),
                reservationDto.getRoom().getDailyRateForPerson(),
                reservationDto.getRoom().getPhotoFileName()
        ));
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
        return new ReservationDto(reservationEntity.getReservationId(), reservationEntity.getDateFrom(), reservationEntity.getDateFrom(), reservationEntity.getCurrency(),
                reservationEntity.getPrice(), reservationEntity.getGuestsInRoom(),
                new CustomerDto(reservationEntity.getCustomerEntity().getCustomerId(), reservationEntity.getCustomerEntity().getFirstName(),
                        reservationEntity.getCustomerEntity().getLastName(), reservationEntity.getCustomerEntity().getEmail(),
                        reservationEntity.getCustomerEntity().getPesel(), reservationEntity.getCustomerEntity().getCreditCardNumber(),
                        reservationEntity.getCustomerEntity().getPhoneNumber()),
                new RoomDto(reservationEntity.getRoomEntity().getRoomId(),
                        reservationEntity.getRoomEntity().getNumber(),
                        reservationEntity.getRoomEntity().getFloor(),
                        reservationEntity.getRoomEntity().getMaxPeopleCapacity(),
                        reservationEntity.getRoomEntity().getDailyRateForPerson(),
                        reservationEntity.getRoomEntity().getPhotoFileName()));
    }

    @Override
    public void deleteReservation(Integer id) {
        reservationRepository.deleteById(id);
    }

    @Override
    public void updateReservation(ReservationDto reservationDto) {
        Optional<ReservationEntity> optionalAccountEntity = reservationRepository.findById(reservationDto.getReservationId());
        if (optionalAccountEntity.isPresent()) {
            ReservationEntity reservationEntity = new ReservationEntity();
            reservationEntity.setReservationId(reservationDto.getReservationId());
            reservationEntity.setDateFrom(reservationDto.getDateFrom());
            reservationEntity.setDateTo(reservationDto.getDateTo());
            reservationEntity.setCurrency(reservationDto.getCurrency());
            reservationEntity.setPrice(reservationDto.getPrice());
            reservationEntity.setGuestsInRoom(reservationDto.getGuestsInRoom());
            reservationEntity.setCustomerEntity(new CustomerEntity(
                    reservationDto.getCustomer().getCustomerId(),
                    reservationDto.getCustomer().getFirstName(),
                    reservationDto.getCustomer().getLastName(),
                    reservationDto.getCustomer().getEmail(),
                    reservationDto.getCustomer().getPesel(),
                    reservationDto.getCustomer().getCreditCardNumber(),
                    reservationDto.getCustomer().getPhoneNumber()));
            reservationRepository.save(reservationEntity);
        }
    }
}
