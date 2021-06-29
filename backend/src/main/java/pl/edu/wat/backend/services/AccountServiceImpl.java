package pl.edu.wat.backend.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.edu.wat.backend.dtos.AccountDto;
import pl.edu.wat.backend.entities.AccountEntity;
import pl.edu.wat.backend.exceptions.AccountConflictException;
import pl.edu.wat.backend.repositories.AccountRepository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class AccountServiceImpl implements AccountService {
    private final AccountRepository accountRepository;
    private final PasswordEncodeService passwordEncodeService;

    @Autowired
    public AccountServiceImpl(AccountRepository accountRepository, PasswordEncodeService passwordEncodeService) {
        this.accountRepository = accountRepository;
        this.passwordEncodeService = passwordEncodeService;
    }

    @Override
    public boolean saveAccount(AccountDto account) {
        String password = passwordEncodeService.getPasswordHash(account.getPassword());
        List<AccountDto> accounts = findAll();
        accounts.forEach(foundAccount -> {
            if (account.getEmail().equals(foundAccount.getEmail())) {
                throw new AccountConflictException();
            }
        });
        AccountEntity accountEntity = new AccountEntity();
        accountEntity.setEmail(account.getEmail());
        accountEntity.setEnabled(account.isEnabled());
        accountEntity.setRole(account.getRole());
        accountEntity.setPassword(password);
        accountEntity.setFirstName(account.getFirstName());
        accountEntity.setLastName(account.getLastName());
        accountEntity.setPhoneNumber(account.getPhoneNumber());
        accountEntity.setCreatedDate(new Date());

        accountRepository.save(accountEntity);
        return true;
    }

    @Override
    public List<AccountDto> findAll() {
        List<AccountDto> accountDtos = new ArrayList<>();
        accountRepository.findAll()
                .forEach(account -> accountDtos.add(new AccountDto(account.getAccountId(), account.getFirstName(), account.getLastName(), account.getEmail(), account.getPassword(), account.isEnabled(), account.getRole(), account.getPhoneNumber(), account.getCreatedDate())));
        return accountDtos;
    }

    @Override
    public AccountDto getAccount(Integer accountId) {
        Optional<AccountEntity> optionalAccountEntity = accountRepository.findById(accountId);
        if (!optionalAccountEntity.isPresent()) {
            return null;
        }
        AccountEntity accountEntity = optionalAccountEntity.get();
        return new AccountDto(accountEntity.getAccountId(), accountEntity.getFirstName(), accountEntity.getLastName(), accountEntity.getEmail(), accountEntity.getPassword(), accountEntity.isEnabled(), accountEntity.getRole(), accountEntity.getPhoneNumber(), accountEntity.getCreatedDate());
    }

    @Override
    public void deleteAccount(Integer accountId) {
        accountRepository.deleteById(accountId);
    }

    @Override
    public void updateAccount(AccountDto accountDto) {
        Optional<AccountEntity> optionalAccountEntity = accountRepository.findById(accountDto.getAccountId());
        if (optionalAccountEntity.isPresent()) {
            AccountEntity accountEntity = optionalAccountEntity.get();
            accountEntity.setEnabled(accountDto.isEnabled());
            accountEntity.setRole(accountDto.getRole());
            accountEntity.setFirstName(accountDto.getFirstName());
            accountEntity.setLastName(accountDto.getLastName());
            accountEntity.setPhoneNumber(accountDto.getPhoneNumber());
            accountRepository.save(accountEntity);
        }
    }
}
