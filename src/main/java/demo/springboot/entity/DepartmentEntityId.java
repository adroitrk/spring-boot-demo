package demo.springboot.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Embeddable
public class DepartmentEntityId implements Serializable {

    @Column(nullable = false, name = "DEPARTMENT")
    private String department;

    @Column(nullable = false, name = "EMP_ID")
    private Integer empId;

}
