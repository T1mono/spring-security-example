package ru.t1.opencschool.springsecurity.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(schema = "corporation", name = "employees")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Employee {
    /**
     * ID сотрудника.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    /**
     * Имя сотрудника.
     */
    @Column(name = "name")
    private String name;

    /**
     * Фамилия сотрудника.
     */
    @Column(name = "surname")
    private String surname;

    /**
     * Отдел в котором работает сотрудник.
     */
    @Column(name = "department")
    private String department;

    /**
     * Зарплата сотрудника.
     */
    @Column(name = "salary")
    private int salary;
}
