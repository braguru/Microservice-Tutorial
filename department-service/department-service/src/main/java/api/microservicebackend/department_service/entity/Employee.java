package api.microservicebackend.department_service.entity;

import jakarta.persistence.Embeddable;

@Embeddable
public record Employee(Long id, String name, int age, String position) {
}
