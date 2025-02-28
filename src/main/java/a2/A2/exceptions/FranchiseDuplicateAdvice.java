package a2.A2.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
class FranchiseDuplicateAdvice {

    @ResponseBody
    @ExceptionHandler(FranchiseDuplicateException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String franchiseDuplicateHandler(FranchiseDuplicateException ex) {
        return ex.getMessage();
    }
}