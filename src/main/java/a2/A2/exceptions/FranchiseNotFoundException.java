package a2.A2.exceptions;

public class FranchiseNotFoundException  extends RuntimeException {

    public FranchiseNotFoundException(Object obj) {
        super("Could not find franchise " + obj.toString());
    }
}