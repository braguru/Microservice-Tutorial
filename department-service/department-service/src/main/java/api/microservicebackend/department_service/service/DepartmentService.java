package api.microservicebackend.department_service.service;

import api.microservicebackend.department_service.client.EmployeeClient;
import api.microservicebackend.department_service.dto.DepartmentListResponse;
import api.microservicebackend.department_service.utils.DepartmentUtils;
import api.microservicebackend.department_service.dto.DepartmentResponse;
import api.microservicebackend.department_service.dto.StatusResponse;
import api.microservicebackend.department_service.entity.Department;
import api.microservicebackend.department_service.payload.DepartmentRequest;
import api.microservicebackend.department_service.repository.DepartmentRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DepartmentService {

    private final EmployeeClient employeeClient;
    private final DepartmentRepository departmentRepository;
    private static final Logger log = LoggerFactory.getLogger(DepartmentService.class);

    public DepartmentResponse addDepartment(DepartmentRequest department) {
        try {
            log.info(DepartmentUtils.ADDING_DEPARTMENT + "{}", department.getName());
            Department existingDepartment = departmentRepository.findByName(department.getName());
            if (existingDepartment != null) {
                log.error(DepartmentUtils.DEPARTMENT_ALREADY_EXISTS + "{}", department.getName());
                return DepartmentResponse.builder()
                        .id(existingDepartment.getId())
                        .name(existingDepartment.getName())
                        .employeeList(existingDepartment.getEmployeeList())
                        .status(new StatusResponse(HttpStatus.CONFLICT.value(),
                                DepartmentUtils.DEPARTMENT_ALREADY_EXISTS + department.getName()))
                        .build();
            }
            Department newDepartment = new Department();
            newDepartment.setName(department.getName());
            departmentRepository.save(newDepartment);
            log.info(DepartmentUtils.DEPARTMENT_CREATED + "{}", department.getName());
            return DepartmentResponse.builder()
                    .id(newDepartment.getId())
                    .name(newDepartment.getName())
                    .employeeList(newDepartment.getEmployeeList())
                    .status(new StatusResponse(HttpStatus.CREATED.value(), DepartmentUtils.DEPARTMENT_CREATED))
                    .build();
        } catch (Exception e) {
            log.error(DepartmentUtils.INTERNAL_SERVER_ERROR + "{}", e.getMessage());
            return DepartmentResponse.builder()
                    .status(new StatusResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                            DepartmentUtils.INTERNAL_SERVER_ERROR))
                    .build();
        }

    }

    public DepartmentResponse getDepartment(Long id) {
        try {
            log.info(DepartmentUtils.RETRIEVING + "{}", id);
            Department department = departmentRepository.findById(id).orElse(null);
            assert department != null;
            log.info(DepartmentUtils.RETRIEVED + "{}", department.getName());
            return DepartmentResponse.builder()
                    .id(department.getId())
                    .name(department.getName())
                    .employeeList(department.getEmployeeList())
                    .status(new StatusResponse(HttpStatus.OK.value(), DepartmentUtils.RETRIEVED))
                    .build();
        } catch (Exception e) {
            log.error(DepartmentUtils.INTERNAL_SERVER_ERROR + "{}", e.getMessage());
            return DepartmentResponse.builder()
                    .status(new StatusResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                            DepartmentUtils.INTERNAL_SERVER_ERROR))
                    .build();
        }
    }

    public DepartmentListResponse getAllDepartments() {
        try {
            log.info(DepartmentUtils.RETRIEVING_ALL);
            List<Department> departmentList = departmentRepository.findAll();
            List<DepartmentResponse> departmentResponses = departmentList.stream().map(department -> DepartmentResponse.builder()
                    .id(department.getId())
                    .name(department.getName())
                    .employeeList(department.getEmployeeList())
                    .build()).toList();
            return DepartmentListResponse.builder()
                    .departments(departmentResponses)
                    .status(new StatusResponse(HttpStatus.OK.value(), DepartmentUtils.RETRIEVED))
                    .build();
        } catch (Exception e) {
            log.error(DepartmentUtils.INTERNAL_SERVER_ERROR + "{}", e.getMessage());
            return DepartmentListResponse.builder()
                    .status(new StatusResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), DepartmentUtils.INTERNAL_SERVER_ERROR))
                    .build();
        }
    }

    public DepartmentListResponse getAllDepartmentsWithEmployees() {
        try {
            log.info(DepartmentUtils.RETRIEVING_ALL);

            // Fetch all departments
            List<Department> departmentList = departmentRepository.findAll();
            log.info(DepartmentUtils.RETRIEVED);

            // Map departments to DepartmentResponse
            List<DepartmentResponse> departmentResponses = departmentList.stream()
                    .map(department -> DepartmentResponse.builder()
                            .id(department.getId())
                            .name(department.getName())
                            .employeeList(employeeClient.getEmployeesByDepartment(department.getId()))
                            .build())
                    .collect(Collectors.toList());

            log.info(DepartmentUtils.DEPARTMENT_MAPPED);

            // Return the response
            return DepartmentListResponse.builder()
                    .departments(departmentResponses)
                    .status(new StatusResponse(HttpStatus.OK.value(), DepartmentUtils.RETRIEVED))
                    .build();

        } catch (Exception e) {
            log.error(DepartmentUtils.INTERNAL_SERVER_ERROR + "{}", e.getMessage());
            return DepartmentListResponse.builder()
                    .status(new StatusResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), DepartmentUtils.INTERNAL_SERVER_ERROR))
                    .build();
        }
    }
}
