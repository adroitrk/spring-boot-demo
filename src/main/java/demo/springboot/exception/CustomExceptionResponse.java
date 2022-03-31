package demo.springboot.exception;

import java.time.Instant;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Setter
@Getter
public class CustomExceptionResponse {

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Instant timestamp;

    private Integer status;

    private String error;

    private String message;

    @JsonInclude(Include.NON_NULL)
    private List<CustomError> errors;

    private String path;
}
