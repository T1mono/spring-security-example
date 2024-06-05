package ru.t1.opencschool.springsecurity.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.t1.opencschool.springsecurity.dto.EmployeeRequestDto;
import ru.t1.opencschool.springsecurity.mapper.EmployeeMapper;
import ru.t1.opencschool.springsecurity.model.Employee;
import ru.t1.opencschool.springsecurity.repository.EmployeeRepo;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepo employeeRepo;

    private final EmployeeMapper employeeMapper;

    @Override
    public Employee createEmployee(EmployeeRequestDto employeeRequestDto) {

        log.info("Создание нового сотрудника с данными: {}", employeeRequestDto);

        // Преобразуем EmployeeRequestDto в Employee с помощью MapStruct
        Employee employee = employeeMapper.entityToEntity(employeeRequestDto);

        log.info("Сохранение сотрудника в базе данных: {}", employee);

        // Сохраняем Employee в базу данных и возвращаем его

        return employeeRepo.save(employee);
    }

    @Override
    public List<EmployeeRequestDto> getAllEmployees() {

        final List<Employee> employees = employeeRepo.findAll();

        log.debug("Get all records with size {}", employees.size());

        return employees
                .stream()
                .map(employeeMapper::entityToDto)
                .collect(Collectors.toList());
    }
}
