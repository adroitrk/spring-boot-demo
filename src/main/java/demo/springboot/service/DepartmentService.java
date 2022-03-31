package demo.springboot.service;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import demo.springboot.entity.DepartmentEntity;
import demo.springboot.entity.DepartmentEntityId;
import demo.springboot.exception.ApplicationException;
import demo.springboot.payload.DepartmentPayload;
import demo.springboot.utils.Constants.Action;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class DepartmentService {

    private demo.springboot.repo.datasourceone.DepartmentRepository departmentRepositoryDataSourceOne;

    private demo.springboot.repo.datasourcetwo.DepartmentRepository departmentRepositoryDataSourceTwo;

    private ModelMapper modelMappper;

    @Transactional
    public String addEmployee(DepartmentPayload departmentPayload) throws ApplicationException {
        DepartmentEntity entity = mapPayloadToEntity(departmentPayload);

        if (!checkEntityExist(entity.getDepartmentEntityId(), Action.CREATE)) {
            departmentRepositoryDataSourceOne.save(entity);
            departmentRepositoryDataSourceTwo.save(entity);
        } else {
            throw new ApplicationException("Employee is already part of a department.", HttpStatus.UNPROCESSABLE_ENTITY);
        }

        return "Employee is added to a department successfully.";
    }

    private DepartmentEntity mapPayloadToEntity(DepartmentPayload departmentPayload) {
        return modelMappper.map(departmentPayload, DepartmentEntity.class);
    }

    private boolean checkEntityExist(DepartmentEntityId entityId, Action action) {

        boolean dataSourceOneEntity = departmentRepositoryDataSourceOne.existsById(entityId);
        boolean dataSourceTwoEntity = departmentRepositoryDataSourceTwo.existsById(entityId);

        return action == Action.CREATE
                            ? dataSourceOneEntity || dataSourceTwoEntity
                            : dataSourceOneEntity && dataSourceTwoEntity;
    }
}
