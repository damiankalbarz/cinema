package com.example.employeeservice.controller;

import com.example.employeeservice.model.Employee;
import com.example.employeeservice.model.VacationRequest;
import com.example.employeeservice.service.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @GetMapping
    public ResponseEntity<List<Employee>> getAllEmployees() {
        List<Employee> employees = employeeService.getAllEmployees();
        return new ResponseEntity<>(employees, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable("id") int id) {
        Employee employee = employeeService.getEmployeeById(id);
        if (employee != null) {
            return new ResponseEntity<>(employee, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<Employee> addEmployee(@Valid @RequestBody Employee employee) {
        Employee savedEmployee = employeeService.saveEmployee(employee);
        return new ResponseEntity<>(savedEmployee, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable("id") int id) {
        employeeService.deleteEmployeeById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/vacation")
    public ResponseEntity<String> takeVacation(@Valid @RequestBody VacationRequest request) {
        boolean success = employeeService.takeVacation(request);
        if (success) {
            return new ResponseEntity<>("Urlop został przyjęty.", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Nie udało się przyjąć urlopu.", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/calculateBonus")
    public ResponseEntity<List<Employee>> calculateBonusForAllEmployees() {
        List<Employee>employees = employeeService.calculateBonusForAllEmployees();
        return new ResponseEntity<>(employees, HttpStatus.OK);
    }

    @PostMapping("/addSickLeave/{id}")
    public ResponseEntity<String> addSickLeave(@PathVariable int id, @RequestBody int days) {
        String result = employeeService.addSickLeave(days,id);
        return ResponseEntity.ok(result);
    }
}
