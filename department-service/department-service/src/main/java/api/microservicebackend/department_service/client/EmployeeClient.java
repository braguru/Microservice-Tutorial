package api.microservicebackend.department_service.client;

import api.microservicebackend.department_service.entity.Employee;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

import java.util.List;

@HttpExchange
public interface EmployeeClient {

    @GetExchange("/employee/department/{departmentId}")
    public List<Employee> getEmployeesByDepartment(@PathVariable Long departmentId);
}
