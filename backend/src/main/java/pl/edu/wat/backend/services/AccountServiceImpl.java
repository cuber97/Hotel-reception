package pl.edu.wat.backend.services;

import org.modelmapper.ModelMapper;
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
    private final ModelMapper modelMapper;

    @Autowired
    public AccountServiceImpl(AccountRepository accountRepository, ModelMapper modelMapper) {
        this.accountRepository = accountRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public boolean saveAccount(AccountDto account) {
        List<AccountDto> accounts = findAll();
        accounts.forEach(foundAccount -> {
            if (account.getEmail().equals(foundAccount.getEmail())) {
                throw new AccountConflictException();
            }
        });
        AccountEntity accountEntity = this.modelMapper.map(account, AccountEntity.class);
        accountEntity.setCreatedDate(new Date());

        accountRepository.save(accountEntity);
        return true;
    }

    @Override
    public List<AccountDto> findAll() {
        List<AccountDto> accountDtos = new ArrayList<>();
        accountRepository.findAll()
                .forEach(account -> accountDtos.add(this.modelMapper.map(account, AccountDto.class)));
        return accountDtos;
    }

    @Override
    public AccountDto getAccount(Integer accountId) {
        Optional<AccountEntity> optionalAccountEntity = accountRepository.findById(accountId);
        if (!optionalAccountEntity.isPresent()) {
            return null;
        }
        AccountEntity accountEntity = optionalAccountEntity.get();
        return this.modelMapper.map(accountEntity, AccountDto.class);
    }

    @Override
    public void deleteAccount(Integer accountId) {
        accountRepository.deleteById(accountId);
    }

    @Override
    public void updateAccount(AccountDto accountDto) {
        Optional<AccountEntity> optionalAccountEntity = accountRepository.findById(accountDto.getAccountId());
        if (optionalAccountEntity.isPresent()) {
            AccountEntity accountEntity = this.modelMapper.map(accountDto, AccountEntity.class);
            accountEntity.setAccountId(optionalAccountEntity.get().getAccountId());
            accountRepository.save(accountEntity);
        }
    }
}
