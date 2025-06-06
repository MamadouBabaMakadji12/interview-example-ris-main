package interview.example.ris.main.employeemanagement.service;

import interview.example.ris.main.employeemanagement.dto.response.DepartmentDto;
import interview.example.ris.main.employeemanagement.entity.Department;
import jakarta.validation.Valid;

import java.util.List;

public interface DepartmentService {
    Department createDepartment(Department department);
    List<DepartmentDto> getAllDepartments();
    Department getDepartmentById(Long id);
    Department updateDepartment(@Valid Department department);
    void deleteDepartment(Long id);
}
