package api.microservicebackend.department_service.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EmployeeResponse {
        private Long id;
        private Long departmentId;
        private String name;
        private int age;
        private String position;
        private StatusResponse status;
}
