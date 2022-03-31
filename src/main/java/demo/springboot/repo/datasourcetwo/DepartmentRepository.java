package demo.springboot.repo.datasourcetwo;

import demo.springboot.entity.DepartmentEntity;
import demo.springboot.entity.DepartmentEntityId;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository("departmentRepoDataSourceTwo")
public interface DepartmentRepository extends CrudRepository<DepartmentEntity, DepartmentEntityId> {
}
