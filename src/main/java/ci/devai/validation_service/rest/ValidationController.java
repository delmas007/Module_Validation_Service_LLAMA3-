package ci.devai.validation_service.rest;

import ci.devai.validation_service.Record.Service;
import ci.devai.validation_service.service.ValidationService;
import ci.devai.validation_service.service.dto.StatutDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/validation-service")
public class ValidationController {
    private final ValidationService validationService;

    @PostMapping
    public StatutDTO validateService(@RequestBody Service service) {
        return validationService.validateService(service);
    }

}
