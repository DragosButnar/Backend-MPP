package a2.A2.exceptions;

public class FranchiseDuplicateException extends RuntimeException {

    public FranchiseDuplicateException(Object obj) {
        super("Franchise already exists! (" + obj.toString() + ")" );
    }
}