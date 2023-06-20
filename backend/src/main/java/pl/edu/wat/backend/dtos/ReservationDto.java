package pl.edu.wat.backend.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReservationDto {
    private Integer reservationId;
    private Date dateFrom;
    private Date dateTo;
    private String currency;
    private BigDecimal price;
    private Integer guestsInRoom;
    private CustomerDto customer;
    private RoomDto room;
}
