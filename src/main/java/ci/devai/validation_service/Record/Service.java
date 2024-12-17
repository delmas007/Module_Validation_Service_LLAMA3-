package ci.devai.validation_service.Record;

import ci.devai.validation_service.model.enume.ServiceStatus;
import ci.devai.validation_service.model.enume.TypeService;

public record Service(TypeService typeService, ServiceStatus status, String description, Float price, Float duration, String cause,Long id_utilisateur) {

}
