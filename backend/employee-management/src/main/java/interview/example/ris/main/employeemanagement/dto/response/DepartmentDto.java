package interview.example.ris.main.employeemanagement.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DepartmentDto {
    private Long id;
    private String name;
    private Long numberOfEmployees;
}
