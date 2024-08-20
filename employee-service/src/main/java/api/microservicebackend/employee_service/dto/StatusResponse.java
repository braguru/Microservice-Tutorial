package api.microservicebackend.employee_service.dto;


import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class StatusResponse {
    int code;
    String message;
}
