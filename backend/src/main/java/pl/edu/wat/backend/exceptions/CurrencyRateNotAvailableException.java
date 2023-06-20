package pl.edu.wat.backend.exceptions;

public class CurrencyRateNotAvailableException extends RuntimeException{
    public CurrencyRateNotAvailableException(String message) {
        super(message);
    }
}
