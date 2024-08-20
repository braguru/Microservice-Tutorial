package api.microservicebackend.department_service.controller;

import api.microservicebackend.department_service.dto.DepartmentListResponse;
import api.microservicebackend.department_service.dto.DepartmentResponse;
import api.microservicebackend.department_service.payload.DepartmentRequest;
import api.microservicebackend.department_service.service.DepartmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/department")
@RequiredArgsConstructor
public class DepartmentController {

    private final DepartmentService departmentService;

    @PostMapping
    public ResponseEntity<DepartmentResponse> addDepartment(@RequestBody DepartmentRequest department) {
        DepartmentResponse response = departmentService.addDepartment(department);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatus().getCode()));
    }

    @GetMapping("{id}")
    public ResponseEntity<DepartmentResponse> department(@PathVariable Long id) {
        DepartmentResponse response = departmentService.getDepartment(id);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatus().getCode()));
    }

    @GetMapping
    public ResponseEntity<DepartmentListResponse> allDepartments() {
        DepartmentListResponse response = departmentService.getAllDepartments();
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatus().getCode()));
    }

    @GetMapping("employees")
    public ResponseEntity<DepartmentListResponse> employeesByDepartment() {
        DepartmentListResponse response = departmentService.getAllDepartmentsWithEmployees();
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatus().getCode()));
    }
}
