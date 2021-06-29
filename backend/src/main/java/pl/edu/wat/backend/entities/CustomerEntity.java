package pl.edu.wat.backend.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="Customer")
public class CustomerEntity {
    @Id
    @Column(name = "CustomerID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer customerId;

    @Column(name = "FirstName")
    private String firstName;

    @Column(name = "LastName")
    private String lastName;

    @Column(name = "Email")
    private String email;

    @Column(name = "Pesel")
    private String pesel;

    @Column(name = "CreditCardNumber")
    private String creditCardNumber;

    @Column(name = "PhoneNumber")
    private String phoneNumber;
}
