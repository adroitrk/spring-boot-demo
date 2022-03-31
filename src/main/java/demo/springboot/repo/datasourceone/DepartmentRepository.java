package demo.springboot.repo.datasourceone;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import demo.springboot.entity.DepartmentEntity;
import demo.springboot.entity.DepartmentEntityId;

@Repository("departmentRepoDataSourceOne")
public interface DepartmentRepository extends CrudRepository<DepartmentEntity, DepartmentEntityId> {
}
