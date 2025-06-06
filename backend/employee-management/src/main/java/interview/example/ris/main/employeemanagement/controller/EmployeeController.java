package interview.example.ris.main.employeemanagement.controller;

import interview.example.ris.main.employeemanagement.dto.request.EmployeeRequestDto;
import interview.example.ris.main.employeemanagement.dto.request.UpdateEmployeeRequestDto;
import interview.example.ris.main.employeemanagement.entity.Employee;
import interview.example.ris.main.employeemanagement.service.EmployeeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/v1/employees")
@RequiredArgsConstructor
public class EmployeeController {
    private final EmployeeService employeeService;

    @PostMapping
    public ResponseEntity<Employee> createEmployee(@Valid @RequestBody EmployeeRequestDto requestDto) {
        Employee createdEmployee = employeeService.createEmployee(requestDto);
        return new ResponseEntity<>(createdEmployee, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Employee> getEmployee(@PathVariable("id") Long id) {
        Employee employee = employeeService.getEmployee(id);
        return new ResponseEntity<>(employee, HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<Employee> updateEmployee(@Valid @RequestBody UpdateEmployeeRequestDto requestDto) {
        Employee updatedEmployee = employeeService.updateEmployee(requestDto);
        return new ResponseEntity<>(updatedEmployee, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable("id") Long id) {
        employeeService.deleteEmployee(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/department/{id}")
    public ResponseEntity<List<Employee>> getEmployeeByDepartment(@PathVariable("id") Long id) {
        List<Employee> employeeList = employeeService.getEmployeeByDepartment(id);
        return new ResponseEntity<>(employeeList, HttpStatus.OK);
    }

    @GetMapping("/department/name/{name}")
    public ResponseEntity<List<Employee>> getEmployeeByDepartment(@PathVariable("name") String name) {
        List<Employee> employeeList = employeeService.getEmployeeByDepartmentName(name);
        return new ResponseEntity<>(employeeList, HttpStatus.OK);
    }

    @GetMapping("/count/department/{id}")
    public ResponseEntity<Long> countEmployeeByDepartment(@PathVariable("id") Long id) {
        Long count = employeeService.countEmployeeByDepartment(id);
        return new ResponseEntity<>(count, HttpStatus.OK);
    }

    @GetMapping("search")
    public ResponseEntity<List<Employee>> searchEmployee(@RequestParam("keyword") String keyword) {
        List<Employee> employees = employeeService.searchEmployee(keyword);
        return new ResponseEntity<>(employees, HttpStatus.OK);
    }
}
