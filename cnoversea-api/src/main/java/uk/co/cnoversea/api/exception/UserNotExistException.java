package uk.co.cnoversea.api.exception;

public class UserNotExistException extends Exception {
    public UserNotExistException(String msg) {
        super(msg);
    }
}
