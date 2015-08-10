package com.exam.blackjack.exception;


import com.exam.blackjack.rest.controller.AppController;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice(assignableTypes = AppController.class)
@ResponseBody
public class GameExceptionHandler {


    @ExceptionHandler(GameException.class)
    public ErrorResponse handle(GameException e) {
        ErrorResponse response = new ErrorResponse();
        response.setErrorMessage(e.getMessage());
        return response;
    }

    @ExceptionHandler(RuntimeException.class)
    public ErrorResponse handle(RuntimeException e) {
        ErrorResponse response = new ErrorResponse();
        response.setErrorMessage("Fatal error. "+e.getMessage());
        return response;
    }
}
