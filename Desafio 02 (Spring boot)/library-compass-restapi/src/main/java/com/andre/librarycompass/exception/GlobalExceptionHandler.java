package com.andre.librarycompass.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {
    /*
    Receive the exception and request in the parameters
    Set the HttpStatus into a variable
    Create a new ErrorResponse passing moment, status value, a title for the error, description (which is the exception message), finally, the uri of the request
    return this ErrorResponse with the HttpStatus variable
     */

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponse> notFoundException(NotFoundException e, HttpServletRequest request){
        HttpStatus status = HttpStatus.NOT_FOUND;
        ErrorResponse response = new ErrorResponse(
                LocalDateTime.now(),
                status.value(),
                "Não encontrado",
                e.getMessage(),
                request.getRequestURI()
        );

        return new ResponseEntity<>(response, status);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> methodArgumentNotValidException(MethodArgumentNotValidException e, HttpServletRequest request){
        HttpStatus status = HttpStatus.BAD_REQUEST;

        StringBuilder erros = new StringBuilder();
        for(FieldError error : e.getBindingResult().getFieldErrors()){
            erros.append(error.getDefaultMessage());
            erros.append(". ");
        }

        ErrorResponse response = new ErrorResponse(
                LocalDateTime.now(),
                status.value(),
                "Argumento inválido",
                erros.toString(),
                request.getRequestURI()
        );

        return new ResponseEntity<>(response, status);
    }

    @ExceptionHandler(BookStatusException.class)
    public ResponseEntity<ErrorResponse> bookStatusException(BookStatusException e, HttpServletRequest request){
        HttpStatus status = HttpStatus.CONFLICT;
        ErrorResponse response = new ErrorResponse(
                LocalDateTime.now(),
                status.value(),
                "Status do livro divergente",
                e.getMessage(),
                request.getRequestURI()
        );

        return new ResponseEntity<>(response, status);
    }

    @ExceptionHandler(DeletionNotAllowedException.class)
    public ResponseEntity<ErrorResponse> deletionNotAllowedException(DeletionNotAllowedException e, HttpServletRequest request){
        HttpStatus status = HttpStatus.CONFLICT;
        ErrorResponse response = new ErrorResponse(
                LocalDateTime.now(),
                status.value(),
                "Exclusão não permitida",
                e.getMessage(),
                request.getRequestURI()
        );

        return new ResponseEntity<>(response, status);
    }
}
