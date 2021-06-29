package pl.edu.wat.backend.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class UnauthorizedException extends RuntimeException {
    public UnauthorizedException() {
        super("Bad credentials");
    }

    public UnauthorizedException(String message) {
        super(message);
    }
}
