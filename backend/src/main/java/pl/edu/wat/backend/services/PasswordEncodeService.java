package pl.edu.wat.backend.services;

import org.springframework.stereotype.Service;

@Service
public interface PasswordEncodeService {
    String getPasswordHash(String password);
}
