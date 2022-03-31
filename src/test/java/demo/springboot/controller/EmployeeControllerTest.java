package demo.springboot.controller;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.sql.SQLException;
import java.util.Date;

import org.hibernate.JDBCException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import demo.springboot.exception.ApplicationException;
import demo.springboot.payload.EmployeePayload;
import demo.springboot.service.EmployeeService;

@WebMvcTest(EmployeeController.class)
public class EmployeeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmployeeService employeeService;

    private static String asJsonString(final Object obj){
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void addEmployeeReturnSuccessStatusForValidRequest() throws Exception {
        String result = "Employee record added";

        when(this.employeeService.addEmployee(any(EmployeePayload.class))).thenReturn(result);

        mockMvc.perform(MockMvcRequestBuilders
                .post("/employee")
                .content(asJsonString(new EmployeePayload(123, "AXC5698V", "dummy", "dummy",
                                                          new Date(), null, "temp")))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(result));
    }

    @Test
    public void throwsExceptionForUnsupportedMethodInRequest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .put("/employee"))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof HttpRequestMethodNotSupportedException))
                .andExpect(jsonPath("$.message").value("Request method 'PUT' not supported"));
    }

    @Test
    public void throwsExceptionForUnprocessedEntityApplicationError() throws Exception {
        String appErrorMsg = "Employee record already exist.";

        when(this.employeeService.addEmployee(any(EmployeePayload.class)))
                .thenThrow(new ApplicationException(appErrorMsg, HttpStatus.UNPROCESSABLE_ENTITY));

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/employee")
                        .content(asJsonString(new EmployeePayload(123, "AXC5698V", "dummy", "dummy",
                                new Date(), null, "temp")))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof ApplicationException))
                .andExpect(jsonPath("$.message").value(appErrorMsg));
    }

    @Test
    public void throwsExceptionForInternalServerApplicationError() throws Exception {
        String appErrorMsg = "Unable to establish JDBC connection.";

        when(this.employeeService.addEmployee(any(EmployeePayload.class)))
                .thenThrow(new JDBCException(appErrorMsg, new SQLException()));

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/employee")
                        .content(asJsonString(new EmployeePayload(123, "AXC5698V", "dummy", "dummy",
                                new Date(), null, "temp")))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof JDBCException))
                .andExpect(jsonPath("$.message").value(appErrorMsg));
    }

    @Test
    public void throwsExceptionForMissingEmpIdInRequest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/employee")
                        .content(asJsonString(new EmployeePayload(null, "AXC5698V", "dummy", "dummy",
                                new Date(), null, "temp")))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof MethodArgumentNotValidException))
                .andExpect(jsonPath("$.message").value("Request is not valid"))
                .andExpect(jsonPath("$.errors[0].message").value("Employee id is mandatory"));
    }

}
