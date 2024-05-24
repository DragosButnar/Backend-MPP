package a2.A2.exceptions;

public class MovieDuplicateException extends RuntimeException {

    public MovieDuplicateException(Object obj) {
        super("Movie title already exists! (" + obj.toString() + ")" );
    }
}