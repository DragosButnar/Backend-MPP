package a2.A2.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class FranchiseNotFoundAdvice {

    @ResponseBody
    @ExceptionHandler(FranchiseNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String franchiseNotFoundHandler(FranchiseNotFoundException ex) {
        return ex.getMessage();
    }
}
