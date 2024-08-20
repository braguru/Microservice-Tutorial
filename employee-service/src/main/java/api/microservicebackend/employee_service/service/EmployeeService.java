package api.microservicebackend.employee_service.service;

import api.microservicebackend.employee_service.dto.EmployeeResponse;
import api.microservicebackend.employee_service.dto.StatusResponse;
import api.microservicebackend.employee_service.entity.Employee;
import api.microservicebackend.employee_service.payload.EmployeeRequest;
import api.microservicebackend.employee_service.repository.EmployeeRepository;
import api.microservicebackend.employee_service.utils.EmployeeUtils;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private static final Logger log = LoggerFactory.getLogger(EmployeeService.class);

    public EmployeeResponse saveEmployee(EmployeeRequest employee) {
        try {
            log.info(EmployeeUtils.SAVING_EMPLOYEE + "{}", employee);
            Employee savedEmployee = employeeRepository.save(Employee.builder()
                    .departmentId(employee.getDepartmentId())
                    .name(employee.getName())
                    .age(employee.getAge())
                    .position(employee.getPosition())
                    .build());
            return EmployeeResponse.builder()
                    .id(savedEmployee.getId())
                    .departmentId(savedEmployee.getDepartmentId())
                    .name(savedEmployee.getName())
                    .age(savedEmployee.getAge())
                    .position(savedEmployee.getPosition())
                    .status(new StatusResponse(HttpStatus.OK.value(), EmployeeUtils.EMPLOYEE_CREATED))
                    .build();
        } catch (Exception e) {
            log.error(EmployeeUtils.ERROR_SAVING_EMPLOYEE + "{}", e.getMessage());
            return EmployeeResponse.builder()
                    .status( new StatusResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), EmployeeUtils.ERROR_SAVING_EMPLOYEE))
                    .build();
        }

    }

    public EmployeeResponse getEmployeeById(Long id) {
        Employee employee = employeeRepository.findById(id).orElse(null);
        if (employee == null) {
            return EmployeeResponse.builder()
                    .status(new StatusResponse(HttpStatus.NOT_FOUND.value(), EmployeeUtils.EMPLOYEE_NOT_FOUND))
                    .build();
        }
        return EmployeeResponse.builder()
                .id(employee.getId())
                .departmentId(employee.getDepartmentId())
                .name(employee.getName())
                .age(employee.getAge())
                .position(employee.getPosition())
                .status(new StatusResponse(HttpStatus.OK.value(), EmployeeUtils.RETRIEVED))
                .build();
    }

    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    public List<EmployeeResponse> findByDepartment(Long departmentId) {
        List<Employee> employee = employeeRepository.findByDepartmentId(departmentId);
        if (employee == null) {
            log.info(EmployeeUtils.EMPLOYEE_NOT_FOUND);
            return List.of(EmployeeResponse.builder()
                    .status(new StatusResponse(HttpStatus.NOT_FOUND.value(), EmployeeUtils.EMPLOYEE_NOT_FOUND))
                    .build());
        }
        log.info(EmployeeUtils.RETRIEVED + "{}", employee);
        return employee.stream()
                .map(employees -> EmployeeResponse.builder()
                        .id(employees.getId())
                        .departmentId(employees.getDepartmentId())
                        .name(employees.getName())
                        .age(employees.getAge())
                        .position(employees.getPosition())
//                        .status(new StatusResponse(HttpStatus.OK.value(), EmployeeUtils.RETRIEVED))
                        .build())
                .collect(Collectors.toList());
    }
}
