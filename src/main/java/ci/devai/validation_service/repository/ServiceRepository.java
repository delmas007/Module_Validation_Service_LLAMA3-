package ci.devai.validation_service.repository;

import ci.devai.validation_service.model.Service;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServiceRepository extends JpaRepository<Service,Long> {
}
