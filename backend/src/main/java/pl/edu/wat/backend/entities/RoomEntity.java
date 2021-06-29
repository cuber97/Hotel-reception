package pl.edu.wat.backend.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@Entity
@Table(name="Room")
@NoArgsConstructor
public class RoomEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="RoomId")
    private Integer roomId;

    @Column(name="Number")
    private Integer number;

    @Column(name="Floor")
    private Integer floor;

    @Column(name="MaxPeopleCapacity")
    private Integer maxPeopleCapacity;

    @Column(name="DailyRateForPerson")
    private BigDecimal dailyRateForPerson;

    @Column(name="PhotoFileName")
    private String photoFileName;
}
