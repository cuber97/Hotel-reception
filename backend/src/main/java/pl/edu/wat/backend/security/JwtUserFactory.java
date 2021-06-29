package pl.edu.wat.backend.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import pl.edu.wat.backend.dtos.AccountDto;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

class JwtUserFactory {
    static JwtUser create(AccountDto accountDto) {
        return new JwtUser(accountDto.getAccountId(), accountDto.getEmail(), accountDto.getPassword(), accountDto,
                mapToGrantedAuthorities(new ArrayList<>(Collections.singletonList("ROLE_" + accountDto.getRole()))), accountDto.isEnabled());
    }

    private static List<GrantedAuthority> mapToGrantedAuthorities(List<String> authorities) {
        return authorities.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
    }
}
