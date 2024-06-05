package ru.t1.opencschool.springsecurity.mapper;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import ru.t1.opencschool.springsecurity.dto.EmployeeRequestDto;
import ru.t1.opencschool.springsecurity.model.Employee;

/**
 * Класс для конвертации записи справочника из entity в DTO.
 */
@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface EmployeeMapper {
    EmployeeRequestDto entityToDto(Employee entity);

    Employee entityToEntity(EmployeeRequestDto dto);
}
