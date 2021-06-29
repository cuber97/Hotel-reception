package pl.edu.wat.backend.entities;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Data
@Entity
@Table(name="Reservation")
public class ReservationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="ReservationId")
    private Integer reservationId;

    @Column(name="DateFrom")
    private Date dateFrom;

    @Column(name="DateTo")
    private Date dateTo;

    @Column(name="Currency")
    private String currency;

    @Column(name="Price")
    private BigDecimal price;

    @Column(name="GuestsInRoom")
    private Integer guestsInRoom;

    @OneToOne
    @JoinColumn(name="CustomerID")
    private CustomerEntity customerEntity;

    @OneToOne
    @JoinColumn(name="RoomID")
    private RoomEntity roomEntity;
}
