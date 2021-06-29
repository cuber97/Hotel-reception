package pl.edu.wat.backend.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoomDto {
    private Integer roomId;
    private Integer number;
    private Integer floor;
    private Integer maxPeopleCapacity;
    private BigDecimal dailyRateForPerson;
    private String photoFileName;
}
