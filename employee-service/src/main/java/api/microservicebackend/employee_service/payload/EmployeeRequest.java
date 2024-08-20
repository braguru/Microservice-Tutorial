package api.microservicebackend.employee_service.payload;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EmployeeRequest{
    private Long departmentId;
    private String name;
    private int age;
    private String position;
}
