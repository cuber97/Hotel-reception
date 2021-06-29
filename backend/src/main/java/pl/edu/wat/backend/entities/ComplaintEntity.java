package pl.edu.wat.backend.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.edu.wat.backend.BackendApplication;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Complaint")
public class ComplaintEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="ComplaintID")
    private Integer complaintId;

    @Column(name="CreationDate")
    private Date creationDate;

    @Column(name="LastUpdateDate")
    private Date lastUpdateDate;

    @Column(name="HasBeenRead")
    private Boolean hasBeenRead;

    @Column(name="Content")
    private String content;

    @OneToOne
    @JoinColumn(name="CustomerID")
    private CustomerEntity customerEntity;
}
