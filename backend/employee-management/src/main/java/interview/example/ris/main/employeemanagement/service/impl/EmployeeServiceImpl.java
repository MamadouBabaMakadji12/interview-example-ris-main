package interview.example.ris.main.employeemanagement.service.impl;

import interview.example.ris.main.employeemanagement.dto.request.EmployeeRequestDto;
import interview.example.ris.main.employeemanagement.dto.request.UpdateEmployeeRequestDto;
import interview.example.ris.main.employeemanagement.entity.Department;
import interview.example.ris.main.employeemanagement.entity.Employee;
import interview.example.ris.main.employeemanagement.exception.DepartmentNotFoundException;
import interview.example.ris.main.employeemanagement.exception.EmployeeNotFoundException;
import interview.example.ris.main.employeemanagement.repository.DepartmentRepository;
import interview.example.ris.main.employeemanagement.repository.EmployeeRepository;
import interview.example.ris.main.employeemanagement.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Log4j2
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;
    private static final String UNASSIGNED = "Unassigned";

    @Override
    public Employee createEmployee(EmployeeRequestDto requestDto) {
        Employee employee = new Employee();
        employee.setFullName(requestDto.getFullName());
        employee.setEmail(requestDto.getEmail());
        employee.setAddress(requestDto.getAddress());
        employee.setPhoneNumber(requestDto.getPhoneNumber());
        // set employee department
        handleEmployeeDepartment(employee, requestDto.getDepartmentName());
        return employeeRepository.save(employee);
    }

    @Override
    public Employee getEmployee(Long id) {
        return employeeRepository.findById(id).orElse(null);
    }

    @Override
    public Employee updateEmployee(UpdateEmployeeRequestDto requestDto) {
        Optional<Employee> employee = employeeRepository.findById(requestDto.getEmployeeId());
        if (employee.isEmpty()) {
            log.error("Employee not found, id: {}", requestDto.getEmployeeId());
            throw new EmployeeNotFoundException("Employee not found");
        }
        Employee employeeToUpdate = employee.get();
        employeeToUpdate.setFullName(requestDto.getFullName());
        employeeToUpdate.setEmail(requestDto.getEmail());
        employeeToUpdate.setAddress(requestDto.getAddress());
        employeeToUpdate.setPhoneNumber(requestDto.getPhoneNumber());
        // set employee department
        handleEmployeeDepartment(employeeToUpdate, requestDto.getDepartmentName());
        return employeeRepository.save(employeeToUpdate);
    }

    @Override
    public void deleteEmployee(Long id) {
        employeeRepository.deleteById(id);
    }

    @Override
    public List<Employee> getEmployeeByDepartment(Long id) {
        verifyDepartment(id);
        return employeeRepository.findByDepartmentId(id);
    }

    @Override
    public Long countEmployeeByDepartment(Long id) {
        verifyDepartment(id);
        return employeeRepository.countByDepartmentId(id);
    }

    @Override
    public List<Employee> searchEmployee(String keyword) {
        return employeeRepository.findByFullNameContainingIgnoreCase(keyword);
    }

    @Override
    public List<Employee> getEmployeeByDepartmentName(String name) {
        if (UNASSIGNED.equalsIgnoreCase(name)) {
            return employeeRepository.findByDepartmentIsNull();
        }
        return employeeRepository.findByDepartmentNameContaining(name);
    }

    private void verifyDepartment(Long departmentId) {
        Optional<Department> department = departmentRepository.findById(departmentId);
        if (department.isEmpty()) {
            log.error("Department not found, id: {}", departmentId);
            throw new DepartmentNotFoundException("Department not found");
        }
    }

    private void handleEmployeeDepartment(Employee employee, String departmentName) {
        if (departmentName != null && !UNASSIGNED.equalsIgnoreCase(departmentName)) {
            Optional<Department> department = departmentRepository.findByName(departmentName);
            // check if department exist, if it not exist throw an exception
            if (department.isPresent()) {
                employee.setDepartment(department.get());
            } else {
                log.error("Department not found, name: {}", departmentName);
                throw new DepartmentNotFoundException("Department not found");
            }
        } else {
            employee.setDepartment(null);
        }
    }
}
