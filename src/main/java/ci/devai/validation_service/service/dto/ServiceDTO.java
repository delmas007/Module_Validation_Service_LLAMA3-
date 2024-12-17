package ci.devai.validation_service.service.dto;

import ci.devai.validation_service.model.Service;
import ci.devai.validation_service.model.enume.ServiceStatus;
import ci.devai.validation_service.model.enume.TypeService;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ServiceDTO {
    private Long id;
    private TypeService typeService;
    private ServiceStatus status;
    private String description;
    private Float price;
    private Float duration;
    private String cause;
    private UtilisateurDTO utilisateur;


}

