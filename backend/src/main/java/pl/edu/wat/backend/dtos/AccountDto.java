package pl.edu.wat.backend.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
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
