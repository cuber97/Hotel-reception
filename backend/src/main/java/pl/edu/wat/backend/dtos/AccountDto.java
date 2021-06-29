package pl.edu.wat.backend.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class AccountDto {
    private Integer accountId;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private boolean enabled;
    private String role;
    private String phoneNumber;
    private Date createdDate;
}
