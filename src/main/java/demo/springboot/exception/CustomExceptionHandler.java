package demo.springboot.exception;


import static demo.springboot.utils.Constants.ALERT_IDENTIFIER;
import static demo.springboot.utils.Constants.ALERT_SEVERITY;

import java.time.Instant;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.ConstraintViolationException;

import org.hibernate.JDBCException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class CustomExceptionHandler {

    @Value("${spring.application.name}")
    private String appName;

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<CustomExceptionResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex, WebRequest request){
        List<CustomError> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(x -> new CustomError(x.getField(), x.getRejectedValue(), x.getDefaultMessage()))
                .collect(Collectors.toList());

        CustomExceptionResponse exceptionResponse = new CustomExceptionResponse(
                Instant.now(),
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                "Request is not valid",
                errors,
                ((ServletWebRequest) request).getRequest().getRequestURI());

        log.error("Status: {} - {}", HttpStatus.BAD_REQUEST.value(), ex.getMessage());
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<CustomExceptionResponse> handleConstraintViolationException(ConstraintViolationException ex, WebRequest request){
        List<CustomError> errors = ex.getConstraintViolations()
                .stream()
                .map(x -> new CustomError(x.getPropertyPath().toString().substring(28),
                                          x.getInvalidValue(), x.getMessage()))
                .collect(Collectors.toList());

        errors.sort(Comparator.comparing(CustomError::getParam));

        CustomExceptionResponse exceptionResponse = new CustomExceptionResponse(
                Instant.now(),
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                "Request is not valid",
                errors,
                ((ServletWebRequest) request).getRequest().getRequestURI());

        log.error("Status: {} - {}", HttpStatus.BAD_REQUEST.value(), ex.getMessage());
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({HttpRequestMethodNotSupportedException.class, HttpMediaTypeNotSupportedException.class,
                       JDBCException.class, ApplicationException.class})
    public ResponseEntity<CustomExceptionResponse> handleGenericException(Exception ex, WebRequest request){
        HttpStatus exceptionStatus = ex instanceof ApplicationException
                                        ? ((ApplicationException) ex).getHttpStatus()
                                        : ex instanceof HttpRequestMethodNotSupportedException
                                                || ex instanceof HttpMediaTypeNotSupportedException
                                            ? HttpStatus.BAD_REQUEST
                                            : HttpStatus.INTERNAL_SERVER_ERROR;

        CustomExceptionResponse exceptionResponse = new CustomExceptionResponse(
                Instant.now(),
                exceptionStatus.value(),
                exceptionStatus.getReasonPhrase(),
                ex.getMessage(),
                null,
                ((ServletWebRequest) request).getRequest().getRequestURI());

        if (exceptionStatus.is5xxServerError()){
            log.error("{} alert_source={} alert_severity={} alert_err_msg={}",
                    ALERT_IDENTIFIER, appName, ALERT_SEVERITY.HIGH, ex.getMessage());
        }

        log.error("Status: {} - {}", exceptionStatus.value(), ex.getMessage());
        return new ResponseEntity<>(exceptionResponse, exceptionStatus);
    }

}
