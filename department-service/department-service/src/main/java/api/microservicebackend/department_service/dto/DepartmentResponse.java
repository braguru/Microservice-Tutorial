package api.microservicebackend.department_service.dto;

import api.microservicebackend.department_service.entity.Employee;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.List;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DepartmentResponse {
    Long id;
    String name;
    List<Employee> employeeList;
    StatusResponse status;

}
