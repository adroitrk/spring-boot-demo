package demo.springboot.repo.datasourceone;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import demo.springboot.entity.EmployeeEntity;

@Repository("employeeRepoDataSourceOne")
public interface EmployeeRepository extends CrudRepository<EmployeeEntity, Integer> {

    EmployeeEntity findByNationalIdentifier(String nationalIdentifier);
}
