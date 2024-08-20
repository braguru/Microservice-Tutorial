package api.microservicebackend.employee_service.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EmployeeResponse {
    private Long id;
    private Long departmentId;
    private String name;
    private int age;
    private String position;
    private StatusResponse status;
}
