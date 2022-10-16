package ro.fasttrackit.proiect.exceptions;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static org.springframework.http.HttpStatus.*;

@RestControllerAdvice
public class ExceptionAdvice {

    private static final String ERROR_CODE = "ERROR";

    private record ApiError(String code, String message) {
    }

    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(NOT_FOUND)
    ApiError handleResourceNotFound(EntityNotFoundException ex) {
        return new ApiError(ERROR_CODE, ex.getMessage());
    }

    @ExceptionHandler(EntityAlreadyExistException.class)
    @ResponseStatus(CONFLICT)
    ApiError handleResourceNotFound(EntityAlreadyExistException ex) {
        return new ApiError(ERROR_CODE, ex.getMessage());
    }

    @ExceptionHandler(InvalidRequestException.class)
    @ResponseStatus(BAD_REQUEST)
    ApiError handleResourceNotFound(InvalidRequestException ex) {
        return new ApiError(ERROR_CODE, ex.getMessage());
    }
}
