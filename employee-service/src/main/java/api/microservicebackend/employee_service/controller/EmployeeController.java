package api.microservicebackend.employee_service.controller;

import api.microservicebackend.employee_service.dto.EmployeeResponse;
import api.microservicebackend.employee_service.entity.Employee;
import api.microservicebackend.employee_service.payload.EmployeeRequest;
import api.microservicebackend.employee_service.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employee")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeResponse> getEmployeeById(@PathVariable Long id) {
        EmployeeResponse employeeResponse = employeeService.getEmployeeById(id);
        return new ResponseEntity<>(employeeResponse , HttpStatusCode.valueOf(employeeResponse.getStatus().getCode()));
    }

    @PostMapping
    public ResponseEntity<EmployeeResponse> saveEmployee(@RequestBody EmployeeRequest employee) {
        EmployeeResponse employeeResponse = employeeService.saveEmployee(employee);
        return new ResponseEntity<>(employeeResponse , HttpStatusCode.valueOf(employeeResponse.getStatus().getCode()));
    }

    @GetMapping
    public List<Employee> getAllEmployees() {
        return employeeService.getAllEmployees();
    }

    @GetMapping("/department/{departmentId}")
    public List<EmployeeResponse> getEmployeesByDepartment(@PathVariable Long departmentId) {
        return employeeService.findByDepartment(departmentId);
    }
}
