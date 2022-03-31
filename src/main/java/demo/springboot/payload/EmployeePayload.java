package demo.springboot.payload;

import java.sql.Date;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Component
public class EmployeePayload {

    @NotNull(message = "Employee id is mandatory")
    @Digits(integer = 6, fraction = 0, message = "Employee id must contain 6 digits")
    private Integer empId;

    @NotBlank(message = "National Identifier is mandatory")
    @Size(min = 8, max = 8, message = "National Identifier must contain 8 characters")
    private String nationalIdentifier;

    @NotBlank(message = "Employee's first name is mandatory")
    private String firstName;

    @NotBlank(message = "Employee's last name is mandatory")
    private String lastName;

    @NotNull(message = "Date of joining is mandatory")
    @JsonFormat(pattern="dd-MMM-yyyy")
    private Date dateOfJoining;

    @JsonFormat(pattern="dd-MMM-yyyy")
    private Date dateOfLeaving;

    @NotBlank(message = "Employment status is mandatory")
    private String status;
}
