package demo.springboot.exception;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Setter
@Getter
@EqualsAndHashCode
class CustomError {

    private String param;

    private Object rejectedValue;

    private String message;
}
