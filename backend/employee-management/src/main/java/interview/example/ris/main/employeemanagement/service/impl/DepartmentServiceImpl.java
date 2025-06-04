package interview.example.ris.main.employeemanagement.service.impl;

import interview.example.ris.main.employeemanagement.dto.response.DepartmentDto;
import interview.example.ris.main.employeemanagement.entity.Department;
import interview.example.ris.main.employeemanagement.entity.Employee;
import interview.example.ris.main.employeemanagement.exception.DepartmentNotFoundException;
import interview.example.ris.main.employeemanagement.repository.DepartmentRepository;
import interview.example.ris.main.employeemanagement.repository.EmployeeRepository;
import interview.example.ris.main.employeemanagement.service.DepartmentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Log4j2
public class DepartmentServiceImpl implements DepartmentService {
    private final DepartmentRepository departmentRepository;
    private final EmployeeRepository employeeRepository;

    @Override
    public Department createDepartment(Department department) {
        return departmentRepository.save(department);
    }

    @Override
    public List<DepartmentDto> getAllDepartments() {
        List<Department> departments = departmentRepository.findAll();
        List<DepartmentDto> departmentDtoList = departments.stream().map(department ->
            DepartmentDto.builder().name(department.getName())
                    .numberOfEmployees(employeeRepository.countByDepartmentId(department.getId())).build()).toList();
        List<Employee> unassignedEmployees = employeeRepository.findByDepartmentIsNull();
        // Add unassigned department if there are unassigned employees
        if (!unassignedEmployees.isEmpty()) {
            departmentDtoList.add(DepartmentDto.builder().name("Unassigned")
                    .numberOfEmployees((long) unassignedEmployees.size()).build());
        }
        return departmentDtoList;
    }

    @Override
    public Department getDepartmentById(Long id) {
        Optional<Department> optionalDepartment = departmentRepository.findById(id);
        if (optionalDepartment.isEmpty()) {
            log.info("Department with id {} not found", id);
            throw new DepartmentNotFoundException("Department not found");
        }
        return optionalDepartment.get();
    }

    @Override
    public Department updateDepartment(Department department) {
        if (department.getId() == null) {
            throw new RuntimeException("Department id must not be null");
        }
        Optional<Department> updatedDepartmentOptional = departmentRepository.findById(department.getId());
        if (updatedDepartmentOptional.isEmpty()) {
            log.info("Department with id {} not found", department.getId());
            throw new DepartmentNotFoundException("Department not found");
        }
        return departmentRepository.save(department);
    }

    @Override
    public void deleteDepartment(Long id) {
        List<Employee> employees = employeeRepository.findByDepartmentId(id);
        // Deleting an existing department moves all previously assigned employees to the "Unassigned" department
        for (Employee employee : employees) {
            employee.setDepartment(null);
        }
        employeeRepository.saveAll(employees);
        departmentRepository.deleteById(id);
    }
}
