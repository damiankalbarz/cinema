package com.example.employeeservice;

import com.example.employeeservice.controller.EmployeeController;
import com.example.employeeservice.model.Employee;
import com.example.employeeservice.model.VacationRequest;
import com.example.employeeservice.service.EmployeeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class EmployeeControllerTest {

    @Mock
    private EmployeeService employeeService;

    @InjectMocks
    private EmployeeController employeeController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllEmployees_ReturnsListOfEmployees() {
        // Given
        List<Employee> employees = new ArrayList<>();
        employees.add(new Employee(100, 1,"adam","Nowak","12345678901","Manager","666888666",6000.0,30,30,0));
        employees.add(new Employee(101, 1,"jakub","Nowak","12345678901","Manager","666888666",6000.0,30,30,0));
        when(employeeService.getAllEmployees()).thenReturn(employees);

        // When
        ResponseEntity<List<Employee>> responseEntity = employeeController.getAllEmployees();

        // Then
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(2, responseEntity.getBody().size());
    }

    @Test
    void getEmployeeById_ExistingId_ReturnsEmployee() {
        // Given
        int employeeId = 100;
        Employee employee = new Employee(employeeId, 1,"adam","Nowak","12345678901","Manager","666888666",6000.0,30,30,30);
        when(employeeService.getEmployeeById(employeeId)).thenReturn(employee);

        // When
        ResponseEntity<Employee> responseEntity = employeeController.getEmployeeById(employeeId);

        // Then
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(employee, responseEntity.getBody());
    }

    @Test
    void getEmployeeById_NonExistingId_ReturnsNotFound() {
        // Given
        int nonExistingId = 103;
        when(employeeService.getEmployeeById(nonExistingId)).thenReturn(null);

        // When
        ResponseEntity<Employee> responseEntity = employeeController.getEmployeeById(nonExistingId);

        // Then
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }

    @Test
    void addEmployee_ValidEmployee_ReturnsCreated() {
        // Given
        Employee employee = new Employee(106, 1,"adam","Nowak","12345678901","Manager","666888666",6000.0,30,30,0);
        when(employeeService.saveEmployee(employee)).thenReturn(employee);

        // When
        ResponseEntity<Employee> responseEntity = employeeController.addEmployee(employee);

        // Then
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(employee, responseEntity.getBody());
    }

    @Test
    void deleteEmployee_ExistingId_ReturnsNoContent() {
        // Given
        int employeeId = 1;

        // When
        ResponseEntity<Void> responseEntity = employeeController.deleteEmployee(employeeId);

        // Then
        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
        verify(employeeService, times(1)).deleteEmployeeById(employeeId);
    }

    @Test
    void deleteEmployee_NonExistingId_ReturnsNotFound() {
        // Given
        int nonExistingId = 107;
        // Assuming deleteEmployeeById does not throw an exception for non-existing ID
        doNothing().when(employeeService).deleteEmployeeById(nonExistingId);

        // When
        ResponseEntity<Void> responseEntity = employeeController.deleteEmployee(nonExistingId);

        // Then
        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
        verify(employeeService, times(1)).deleteEmployeeById(nonExistingId);
    }

    @Test
    void takeVacation_ValidVacationRequest_ReturnsOk() {
        // Given
        VacationRequest request = new VacationRequest(/*provide necessary details*/);
        when(employeeService.takeVacation(request)).thenReturn(true);

        // When
        ResponseEntity<String> responseEntity = employeeController.takeVacation(request);

        // Then
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("Urlop został przyjęty.", responseEntity.getBody());
    }

    @Test
    void takeVacation_InvalidVacationRequest_ReturnsBadRequest() {
        // Given
        VacationRequest request = new VacationRequest(/*provide necessary details*/);
        when(employeeService.takeVacation(request)).thenReturn(false);

        // When
        ResponseEntity<String> responseEntity = employeeController.takeVacation(request);

        // Then
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals("Nie udało się przyjąć urlopu.", responseEntity.getBody());
    }


}
