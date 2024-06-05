package ru.t1.opencschool.springsecurity.service;

import org.springframework.transaction.annotation.Transactional;
import ru.t1.opencschool.springsecurity.dto.EmployeeRequestDto;
import ru.t1.opencschool.springsecurity.model.Employee;

import java.util.List;

public interface EmployeeService {
    @Transactional
    Employee createEmployee(EmployeeRequestDto employeeRequestDto);

    List<EmployeeRequestDto> getAllEmployees();
}
