package demo.springboot.repo.datasourcetwo;

import demo.springboot.entity.EmployeeEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository("employeeRepoDataSourceTwo")
public interface EmployeeRepository extends CrudRepository<EmployeeEntity, Integer> {
}
