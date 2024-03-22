package com.example.employeeservice.service;

import com.example.employeeservice.model.Employee;
import com.example.employeeservice.model.VacationRequest;
import com.example.employeeservice.repository.EmployeeRepository;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Value("${rabbitmq.exchange}")
    private String exchange;

    @Value("${rabbitmq.routingKey}")
    private String routingKey;

    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    public Employee getEmployeeById(int id) {
        return employeeRepository.findById(id).orElse(null);
    }

    public Employee saveEmployee(Employee employee) {
        rabbitTemplate.convertAndSend(exchange, routingKey, employee);
        return employeeRepository.save(employee);
    }

    public void deleteEmployeeById(int id) {
        employeeRepository.deleteById(id);
    }

    public boolean takeVacation(VacationRequest request) {
        Employee employee = employeeRepository.findById(request.getEmployeeId()).orElse(null);
        if (employee != null) {
            int vacationDays = calculateVacationDays(request.getStartDate(), request.getEndDate());
            if (vacationDays > 0 && vacationDays <= employee.getAvailableVacationDays()) {
                employee.setAvailableVacationDays(employee.getAvailableVacationDays() - vacationDays);
                employeeRepository.save(employee);
                return true;
            }
        }
        return false;
    }

    private int calculateVacationDays(Date startDate, Date endDate) {
        LocalDate startLocalDate = convertToLocalDate(startDate);
        LocalDate endLocalDate = convertToLocalDate(endDate);

        // Obliczenie liczby dni między datami
        long daysBetween = ChronoUnit.DAYS.between(startLocalDate, endLocalDate);

        // Dodatkowe dni, ponieważ ostatni dzień urlopu również jest uwzględniany
        return (int) daysBetween + 1;
    }

    private LocalDate convertToLocalDate(Date dateToConvert) {
        return new java.sql.Date(dateToConvert.getTime()).toLocalDate();
    }

}
