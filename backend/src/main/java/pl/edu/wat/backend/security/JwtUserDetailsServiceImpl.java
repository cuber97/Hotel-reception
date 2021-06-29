package pl.edu.wat.backend.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pl.edu.wat.backend.dtos.AccountDto;
import pl.edu.wat.backend.entities.AccountEntity;
import pl.edu.wat.backend.repositories.AccountRepository;

@Service
public class JwtUserDetailsServiceImpl implements UserDetailsService {
    private AccountRepository accountRepository;

    @Autowired
    public JwtUserDetailsServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AccountEntity accountEntity = accountRepository.findByEmailIgnoreCase(username);
        if(accountEntity == null) {
            throw new UsernameNotFoundException(String.format("No User found with username '%s'.", username));
        } else {
            AccountDto accountDto = new AccountDto(accountEntity.getAccountId(), accountEntity.getFirstName(),
                    accountEntity.getLastName(), accountEntity.getEmail(), accountEntity.getPassword(), accountEntity.isEnabled(),
                    accountEntity.getRole(), accountEntity.getPhoneNumber(), accountEntity.getCreatedDate());
            return JwtUserFactory.create(accountDto);
        }
    }
}
