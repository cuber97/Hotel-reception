package pl.edu.wat.backend.exceptions;

import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.SpringSecurityMessageSource;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class AccountConflictException extends RuntimeException {
    public AccountConflictException() {
        super("Given email is already used by another account.");
    }

    public AccountConflictException(String message) {
        super(message);
    }
}
