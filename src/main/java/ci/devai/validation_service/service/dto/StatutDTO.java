package ci.devai.validation_service.service.dto;

import ci.devai.validation_service.model.enume.ServiceStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StatutDTO {
    ServiceStatus status;
    String cause;

}
