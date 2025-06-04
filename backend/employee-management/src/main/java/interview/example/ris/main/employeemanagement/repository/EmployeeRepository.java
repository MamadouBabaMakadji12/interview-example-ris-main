package interview.example.ris.main.employeemanagement.repository;

import interview.example.ris.main.employeemanagement.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    List<Employee> findByDepartmentId(Long departmentId);
    Long countByDepartmentId(Long departmentId);
    List<Employee> findByFullNameContainingIgnoreCase(String keyword);
    // Unassigned department employees
    List<Employee> findByDepartmentIsNull();
}
