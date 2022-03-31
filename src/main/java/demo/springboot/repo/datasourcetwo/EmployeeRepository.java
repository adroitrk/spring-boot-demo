package demo.springboot.repo.datasourcetwo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import demo.springboot.entity.EmployeeEntity;

@Repository("employeeRepoDataSourceTwo")
public interface EmployeeRepository extends CrudRepository<EmployeeEntity, Integer> {

    EmployeeEntity findByNationalIdentifier(String nationalIdentifier);
}
