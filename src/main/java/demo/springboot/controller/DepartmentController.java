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
import demo.springboot.payload.DepartmentPayload;
import demo.springboot.service.DepartmentService;
import demo.springboot.utils.Constants;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Validated
@RestController
@RequestMapping("/department")
public class DepartmentController {

    private DepartmentService service;

    public DepartmentController(DepartmentService service) {
        this.service = service;
    }

    @PostMapping(value = "/employee",
                 consumes = MediaType.APPLICATION_JSON_VALUE,
                 produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> addEmployee(@Valid @RequestBody DepartmentPayload departmentPayload)
                                                        throws ApplicationException {

        logPayload(Arrays.asList(departmentPayload), Constants.Action.CREATE.getLabel());
        String response = service.addEmployee(departmentPayload);
        log.info("Status: {} - {}", HttpStatus.OK.value(), response);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    private void logPayload(List<DepartmentPayload> payloadList, String action){
        payloadList.forEach(payload ->
                log.info("Processing request for: "
                                + "department:{}, employee id:{}, unit:{} and position:{}"
                                + " with action:{}",
                        payload.getDepartment(), payload.getEmpId(), payload.getUnit(), payload.getPosition(), action));
    }
    
}
