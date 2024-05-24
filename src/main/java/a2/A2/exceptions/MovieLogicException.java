package a2.A2.exceptions;

public class MovieLogicException extends RuntimeException {

    public MovieLogicException(Object obj) {
        super("Movie logic is incorrect! (" + obj.toString() + ")" );
    }
}