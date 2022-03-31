package demo.springboot.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.sql.Date;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Entity
@Table(name = "EMPLOYEE")
public class EmployeeEntity implements Serializable {

    @Id
    @Column(nullable = false, name = "EMP_ID")
    private Integer empId;

    @Column(nullable = false, name = "NATIONAL_IDENTIFIER")
    private String nationalIdentifier;

    @Column(nullable = false, name = "FIRST_NAME")
    private String firstName;

    @Column(nullable = false, name = "LAST_NAME")
    private String lastName;

    @Column(nullable = false, name = "DOJ")
    private Date dateOfJoining;

    @Column(name = "DOL")
    private Date dateOfLeaving;

    @Column(nullable = false, name = "STATUS")
    private String status;
}
