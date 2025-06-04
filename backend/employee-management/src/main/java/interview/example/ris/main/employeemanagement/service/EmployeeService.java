package interview.example.ris.main.employeemanagement.service;

import interview.example.ris.main.employeemanagement.dto.request.EmployeeRequestDto;
import interview.example.ris.main.employeemanagement.dto.request.UpdateEmployeeRequestDto;
import interview.example.ris.main.employeemanagement.entity.Employee;

import java.util.List;

public interface EmployeeService {
    Employee createEmployee(EmployeeRequestDto requestDto);
    Employee getEmployee(Long id);
    Employee updateEmployee(UpdateEmployeeRequestDto requestDto);
    void deleteEmployee(Long id);
    List<Employee> getEmployeeByDepartment(Long id);
    Long countEmployeeByDepartment(Long id);
    List<Employee> searchEmployee(String keyword);
}
