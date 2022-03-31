package demo.springboot.repo.datasourceone;

import demo.springboot.entity.EmployeeEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository("employeeRepoDataSourceOne")
public interface EmployeeRepository extends CrudRepository<EmployeeEntity, Integer> {

    EmployeeEntity findByNationalIdentifier(String nationalIdentifier);
}
