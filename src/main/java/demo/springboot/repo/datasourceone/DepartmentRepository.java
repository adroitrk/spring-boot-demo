package demo.springboot.repo.datasourceone;

import demo.springboot.entity.DepartmentEntity;
import demo.springboot.entity.DepartmentEntityId;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository("departmentRepoDataSourceOne")
public interface DepartmentRepository extends CrudRepository<DepartmentEntity, DepartmentEntityId> {
}
