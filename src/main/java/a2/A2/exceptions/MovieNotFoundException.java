package a2.A2.exceptions;


public class MovieNotFoundException extends RuntimeException {

    public MovieNotFoundException(Object obj) {
        super("Could not find movie " + obj.toString());
    }
}
