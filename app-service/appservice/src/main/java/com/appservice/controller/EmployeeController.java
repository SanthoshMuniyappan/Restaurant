package com.appservice.controller;

import com.appservice.dto.EmployeeDTO;
import com.appservice.dto.ResponseDTO;
import com.appservice.service.EmployeeService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/employee")
public class EmployeeController {


    private final EmployeeService employeeService;

    public EmployeeController(final EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @PostMapping("/create")
    public ResponseDTO create(@RequestBody final EmployeeDTO employeeDTO) {
        return this.employeeService.create(employeeDTO);
    }

    @PutMapping("/update/{id}")
    public ResponseDTO update(@PathVariable final String id, @RequestBody EmployeeDTO employeeDTO) {
        return this.employeeService.update(id, employeeDTO);
    }

    @GetMapping("/retrieve/{id}")
    public ResponseDTO retrieve(@PathVariable final String id) {
        return this.employeeService.retrieve(id);
    }

    @GetMapping("/retrieve-all")
    public ResponseDTO retrieveAll() {
        return this.employeeService.retrieveAll();
    }

    @DeleteMapping("/remove/{id}")
    public ResponseDTO remove(@PathVariable final String id) {
        return this.employeeService.remove(id);
    }
}
