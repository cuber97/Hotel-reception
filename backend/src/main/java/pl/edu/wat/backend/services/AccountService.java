package pl.edu.wat.backend.services;

import org.springframework.stereotype.Service;
import pl.edu.wat.backend.dtos.AccountDto;

import java.util.List;

@Service
public interface AccountService {
    boolean saveAccount(AccountDto account);
    List<AccountDto> findAll();
    AccountDto getAccount(Integer accountId);
    void deleteAccount(Integer accountId);
    void updateAccount(AccountDto accountDto);
}
