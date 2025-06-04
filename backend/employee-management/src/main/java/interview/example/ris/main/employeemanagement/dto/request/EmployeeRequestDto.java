package interview.example.ris.main.employeemanagement.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class EmployeeRequestDto {
    @NotBlank
    private String fullName;
    @NotBlank
    private String address;
    @NotBlank
    private String phoneNumber;
    @NotBlank
    @Email
    private String email;
    private Long departmentId;
}
