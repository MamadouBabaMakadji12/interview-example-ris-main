package interview.example.ris.main.employeemanagement.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class UpdateEmployeeRequestDto extends EmployeeRequestDto {
    @NotNull(message = "Employee Id cannot be null")
    private Long employeeId;
}
