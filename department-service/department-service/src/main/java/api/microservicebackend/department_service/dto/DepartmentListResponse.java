package api.microservicebackend.department_service.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class DepartmentListResponse {
    private List<DepartmentResponse> departments;
    private StatusResponse status;
}
