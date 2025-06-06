package interview.example.ris.main.employeemanagement;

import interview.example.ris.main.employeemanagement.dto.request.EmployeeRequestDto;
import interview.example.ris.main.employeemanagement.entity.Employee;
import interview.example.ris.main.employeemanagement.repository.EmployeeRepository;
import interview.example.ris.main.employeemanagement.service.impl.EmployeeServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@DataJpaTest
@Import(EmployeeServiceImpl.class)
public class EmployeeServiceImplTest {
    @Autowired
    private EmployeeServiceImpl employeeService;

    @Autowired
    private EmployeeRepository employeeRepository;

    private Employee employee;

    @BeforeEach
    public void setUp() {
        EmployeeRequestDto newEmployee = new EmployeeRequestDto();
        newEmployee.setFullName("Mamadou MAKADJI");
        newEmployee.setEmail("mamadou.mak@dev.com");
        newEmployee.setDepartmentName(null);

        employee = employeeService.createEmployee(newEmployee);
    }

    @Test
    public void testFindById() {
        Employee result = employeeService.getEmployee(employee.getId());
        assertThat(result).isNotNull();
        assertThat(result.getFullName()).isEqualTo("Mamadou MAKADJI");
        assertThat(result.getDepartment()).isNull();
    }

    @Test
    public void testSearchByFullName() {
        List<Employee> employees = employeeService.searchEmployee("ado");
        assertThat(employees).hasSize(1);
    }

    @Test
    public void testDeleteEmployee() {
        employeeService.deleteEmployee(employee.getId());
        List<Employee> employees = employeeRepository.findAll();
        assertThat(employees).isEmpty();
    }
}
