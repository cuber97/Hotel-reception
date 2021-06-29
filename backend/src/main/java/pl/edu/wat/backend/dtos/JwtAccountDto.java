package pl.edu.wat.backend.dtos;

import lombok.Data;

@Data
public class JwtAccountDto {
    private AccountDto account;
    private String token;

    public JwtAccountDto(AccountDto account, String token) {
        this.account = account;
        this.token = token;
    }
}
