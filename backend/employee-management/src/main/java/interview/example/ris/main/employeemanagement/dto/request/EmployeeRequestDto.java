package interview.example.ris.main.employeemanagement.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class EmployeeRequestDto {
    @NotBlank(message = "Invalid fullName")
    private String fullName;
    @NotBlank(message = "Invalid address")
    private String address;
    @NotBlank(message = "Phone number can not be blank")
    private String phoneNumber;
    @NotBlank
    @Email(message = "Invalid email")
    private String email;
    private String departmentName;
}
