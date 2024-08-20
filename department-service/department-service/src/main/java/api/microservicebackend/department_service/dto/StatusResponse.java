package api.microservicebackend.department_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class StatusResponse {
    int code;
    String message;
}
