package pl.edu.wat.backend.services;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class PasswordEncodeServiceImpl implements PasswordEncodeService{
    private final static BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public String getPasswordHash(String password) {
        return encoder.encode(password);
    }
}
