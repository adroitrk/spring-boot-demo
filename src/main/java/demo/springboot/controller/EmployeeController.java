package demo.springboot.controller;

import java.util.Arrays;
import java.util.List;
import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import demo.springboot.exception.ApplicationException;
import demo.springboot.payload.EmployeePayload;
import demo.springboot.service.EmployeeService;
import demo.springboot.utils.Constants.Action;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Validated
@RestController
@RequestMapping("/employee")
public class EmployeeController {

    private EmployeeService service;

    public EmployeeController(EmployeeService service) {
        this.service = service;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
                 produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> addEmployee(@Valid @RequestBody EmployeePayload employeePayload)
                                                                    throws ApplicationException {
        logPayload(Arrays.asList(employeePayload), Action.CREATE.getLabel());
        String response = service.addEmployee(employeePayload);
        log.info("Status: {} - {}", HttpStatus.OK.value(), response);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    private void logPayload(List<EmployeePayload> payloadList, String action){
        payloadList.forEach(payload ->
                log.info("Processing request for: "
                                + "employee id:{}, first name:{}, last name:{},"
                                + " date of joining:{} and employment status:{} with action:{}",
                        payload.getEmpId(), payload.getFirstName(), payload.getLastName(),
                        payload.getDateOfJoining(), payload.getStatus(), action));
    }
}
