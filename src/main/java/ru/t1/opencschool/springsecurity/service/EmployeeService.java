package ru.t1.opencschool.springsecurity.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.t1.opencschool.springsecurity.repository.EmployeeRepo;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepo employeeRepo;


    public void createEmployee() {

    }
}
