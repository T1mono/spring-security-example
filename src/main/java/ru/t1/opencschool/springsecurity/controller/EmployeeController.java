package ru.t1.opencschool.springsecurity.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.t1.opencschool.springsecurity.dto.EmployeeRequestDto;
import ru.t1.opencschool.springsecurity.model.Employee;
import ru.t1.opencschool.springsecurity.service.EmployeeService;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Slf4j
public class EmployeeController {

    private final EmployeeService employeeService;

    @GetMapping("/employees")
    public List<EmployeeRequestDto> allEmployees() {
        log.debug("Получение списка всех сотрудников");
        List<EmployeeRequestDto> result = employeeService.getAllEmployees();
        log.debug("Список всех сотрудников получен успешно");
        return result;
    }

    @PostMapping("/add-employee")
    public Employee createEmployee(@RequestBody EmployeeRequestDto employeeRequestDto) {
        return employeeService.createEmployee(employeeRequestDto);
    }
}
