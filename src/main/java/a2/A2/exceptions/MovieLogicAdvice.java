package a2.A2.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
class MovieLogicAdvice {

    @ResponseBody
    @ExceptionHandler(MovieLogicException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String movieLogicHandler(MovieLogicException ex) {
        return ex.getMessage();
    }
}