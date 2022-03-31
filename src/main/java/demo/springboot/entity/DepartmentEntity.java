package demo.springboot.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Entity
@Table(name = "DEPARTMENT")
public class DepartmentEntity implements Serializable {

    @EmbeddedId
    private DepartmentEntityId departmentEntityId;

    @Column(nullable = false, name = "UNIT")
    private String unit;

    @Column(nullable = false, name = "POSITION")
    private String position;
}
