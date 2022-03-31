package demo.springboot.payload;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Component
public class DepartmentPayload {

    @NotBlank(message = "Department is mandatory")
    private String department;

    @NotNull(message = "Employee id is mandatory")
    @Digits(integer = 6, fraction = 0, message = "Employee id must contain 6 digits")
    private Integer empId;

    @NotBlank(message = "Unit is mandatory")
    private String unit;

    @NotBlank(message = "Employee's Position is mandatory")
    private String position;
}
