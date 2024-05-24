package a2.A2.exceptions;

public class UserNotFoundException  extends RuntimeException {
    public UserNotFoundException(Object obj) {
        super("Could not find user " + obj.toString());
    }
}
