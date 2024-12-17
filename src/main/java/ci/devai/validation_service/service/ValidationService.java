package ci.devai.validation_service.service;

import ci.devai.validation_service.Record.Service;
import ci.devai.validation_service.service.dto.StatutDTO;

public interface ValidationService {
    StatutDTO validateService(Service service);
    Service validateServicee(Service service);
}
