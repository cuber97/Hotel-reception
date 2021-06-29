package pl.edu.wat.backend.exceptions;

public class RoomExistenceException extends RuntimeException{
    public RoomExistenceException(String message) {
        super(message);
    }
}
