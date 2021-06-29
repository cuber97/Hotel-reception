package pl.edu.wat.backend.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CustomerDto {
    private Integer customerId;
    private String firstName;
    private String lastName;
    private String email;
    private String pesel;
    private String creditCardNumber;
    private String phoneNumber;
}
