package demo.springboot.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.http.HttpStatus;

import demo.springboot.entity.EmployeeEntity;
import demo.springboot.exception.ApplicationException;
import demo.springboot.payload.EmployeePayload;

@ExtendWith(MockitoExtension.class)
public class EmployeeServiceTest {

    @Mock
    private demo.springboot.repo.datasourceone.EmployeeRepository employeeRepositoryDataSourceOne;

    @Mock
    private demo.springboot.repo.datasourcetwo.EmployeeRepository employeeRepositoryDataSourceTwo;

    @Spy
    private ModelMapper mapper;

    @InjectMocks
    private EmployeeService service;

    private EmployeeEntity testEmployeeEntity;

    private EmployeePayload testEmployeePayload;

    @BeforeEach
    void setUp() {
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
        testEmployeeEntity = new EmployeeEntity();
        testEmployeePayload = new EmployeePayload(123, "AXC5698V", "dummy", "dummy",
                new Date(), null, "temp");
    }

    @Test
    public void addEmployeeReturnsSuccessfulResponse() throws ApplicationException {
        lenient().when(this.employeeRepositoryDataSourceOne
                .findByNationalIdentifier(anyString())).thenReturn(null);
        lenient().when(this.employeeRepositoryDataSourceTwo
                .findByNationalIdentifier(anyString())).thenReturn(null);
        lenient().when(this.employeeRepositoryDataSourceOne.save(any(EmployeeEntity.class)))
                .thenReturn(testEmployeeEntity);
        lenient().when(this.employeeRepositoryDataSourceTwo.save(any(EmployeeEntity.class)))
                .thenReturn(testEmployeeEntity);

        String response = service.addEmployee(testEmployeePayload);
        assertEquals("Employee record created successfully.", response);
    }

    @Test
    public void addEmployeeThrowsExceptionWhenEmployeeRecordAlreadyExistInAllDatabase() throws ApplicationException {
        lenient().when(this.employeeRepositoryDataSourceOne
                .findByNationalIdentifier(anyString())).thenReturn(testEmployeeEntity);
        lenient().when(this.employeeRepositoryDataSourceTwo
                .findByNationalIdentifier(anyString())).thenReturn(testEmployeeEntity);
        lenient().when(this.employeeRepositoryDataSourceOne.save(any(EmployeeEntity.class)))
                .thenReturn(testEmployeeEntity);
        lenient().when(this.employeeRepositoryDataSourceTwo.save(any(EmployeeEntity.class)))
                .thenReturn(testEmployeeEntity);

        ApplicationException applicationException =
                assertThrows(ApplicationException.class, () -> service.addEmployee(testEmployeePayload));

        assertEquals(applicationException.getHttpStatus(), HttpStatus.UNPROCESSABLE_ENTITY);
        assertEquals(applicationException.getMessage(), "Employee record already exist.");
    }

    @Test
    public void addEmployeeThrowsExceptionWhenEmployeeRecordAlreadyExistInAnyDatabase() throws ApplicationException {
        lenient().when(this.employeeRepositoryDataSourceOne
                .findByNationalIdentifier(anyString())).thenReturn(testEmployeeEntity);
        lenient().when(this.employeeRepositoryDataSourceTwo
                .findByNationalIdentifier(anyString())).thenReturn(null);
        lenient().when(this.employeeRepositoryDataSourceOne.save(any(EmployeeEntity.class)))
                .thenReturn(testEmployeeEntity);
        lenient().when(this.employeeRepositoryDataSourceTwo.save(any(EmployeeEntity.class)))
                .thenReturn(testEmployeeEntity);

        ApplicationException applicationException =
                assertThrows(ApplicationException.class, () -> service.addEmployee(testEmployeePayload));

        assertEquals(applicationException.getHttpStatus(), HttpStatus.UNPROCESSABLE_ENTITY);
        assertEquals(applicationException.getMessage(), "Employee record already exist.");
    }
}
