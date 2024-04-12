package com.example.employeeservice.service;

import com.example.employeeservice.dto.SickLeave;
import com.example.employeeservice.model.Employee;
import com.example.employeeservice.model.VacationRequest;
import com.example.employeeservice.repository.EmployeeRepository;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private Jackson2JsonMessageConverter jsonMessageConverter;
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
        rabbitTemplate.setMessageConverter(jsonMessageConverter);
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

    public Employee editEmployee(int id, Employee updatedEmployee) {
        Optional<Employee> optionalEmployee = employeeRepository.findById(id);
        if (optionalEmployee.isPresent()) {
            Employee existingEmployee = optionalEmployee.get();
            existingEmployee.setCinemaId(updatedEmployee.getCinemaId());
            existingEmployee.setName(updatedEmployee.getName());
            existingEmployee.setSurname(updatedEmployee.getSurname());
            existingEmployee.setPosition(updatedEmployee.getPosition());
            existingEmployee.setPhone(updatedEmployee.getPhone());
            existingEmployee.setSalary(updatedEmployee.getSalary());
            existingEmployee.setHolidaysDays(updatedEmployee.getHolidaysDays());
            existingEmployee.setDaysOnSickLeave(updatedEmployee.getDaysOnSickLeave());
            existingEmployee.setAvailableVacationDays(updatedEmployee.getAvailableVacationDays());

            return employeeRepository.save(existingEmployee);
        } else {
            throw new IllegalArgumentException("Nie można znaleźć pracownika o podanym identyfikatorze: " + id);
        }
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

    public List<Employee> calculateBonusForAllEmployees() {
        List<Employee> employees = getAllEmployees();
        for (Employee employee : employees) {
            employee.calculateBonus();
            //saveEmployee(employee);
        }
        return employees;
    }



    public String addSickLeave(int days, int id) {
        if (days < 1) {
            throw new IllegalArgumentException("Liczba dni na zwolnieniu lekarskim nie może być ujemna");
        }
        Employee employee = employeeRepository.findById(id).orElse(null);

        System.out.println(employee.getId());
        employee.setDaysOnSickLeave(employee.getDaysOnSickLeave()+days);

        editEmployee(id,employee);

        return "Zwolnienie lekarskie dodane pomyślnie";
    }

    @KafkaListener(
            topics = "sickLeave",
            groupId = "sickLeave"
    )
    void listener(String data){
        try{
            System.out.println("\nData received: " + data);
            ObjectMapper mapper = new ObjectMapper();
            JsonParser parser = mapper.createParser(data);
            SickLeave sickLeave =  parser.readValueAs(SickLeave.class);
            System.out.println(sickLeave);
            Optional<Employee> optionalEmployee = employeeRepository.findByPesel(sickLeave.getPesel());
            if (optionalEmployee.isPresent()) {
                Employee existingEmployee = optionalEmployee.get();
                addSickLeave(sickLeave.getDaysOfSickLeave(),existingEmployee.getId());
            }


        }catch(Exception e){
            e.printStackTrace();
        }
    }

}
