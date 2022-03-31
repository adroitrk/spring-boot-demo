package demo.springboot.service;

import demo.springboot.entity.EmployeeEntity;
import demo.springboot.exception.ApplicationException;
import demo.springboot.payload.EmployeePayload;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@AllArgsConstructor
@Service
public class EmployeeService {

    private demo.springboot.repo.datasourceone.EmployeeRepository employeeRepositoryDataSourceOne;

    private demo.springboot.repo.datasourcetwo.EmployeeRepository employeeRepositoryDataSourceTwo;

    private ModelMapper modelMappper;

    @Transactional
    public String addEmployee(EmployeePayload employeePayload) throws ApplicationException {
        EmployeeEntity entity = mapPayloadToEntity(employeePayload);

        if (!checkEntityExist(entity)) {
            employeeRepositoryDataSourceOne.save(entity);
            employeeRepositoryDataSourceTwo.save(entity);
        } else {
            throw new ApplicationException("Employee record already exist.", HttpStatus.UNPROCESSABLE_ENTITY);
        }

        return "Employee record created successfully.";
    }

    private EmployeeEntity mapPayloadToEntity(EmployeePayload employeePayload) {
        return modelMappper.map(employeePayload, EmployeeEntity.class);
    }

    private boolean checkEntityExist(EmployeeEntity entity) {

        EmployeeEntity dataSourceOneEntity = employeeRepositoryDataSourceOne
                                                .findByNationalIdentifier(entity.getNationalIdentifier());

        EmployeeEntity dataSourceTwoEntity = employeeRepositoryDataSourceOne
                                                .findByNationalIdentifier(entity.getNationalIdentifier());

        return null != dataSourceOneEntity && null != dataSourceTwoEntity
                    && dataSourceOneEntity.equals(dataSourceTwoEntity);

    }
}
