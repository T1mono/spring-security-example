package ru.t1.opencschool.springsecurity.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.t1.opencschool.springsecurity.model.Employee;

@Repository
public interface EmployeeRepo extends JpaRepository<Employee, Long> {

}
