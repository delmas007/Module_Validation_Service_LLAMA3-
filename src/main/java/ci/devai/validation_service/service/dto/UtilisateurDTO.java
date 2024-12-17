package ci.devai.validation_service.service.dto;

import ci.devai.validation_service.model.Utilisateur;
import lombok.*;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UtilisateurDTO {
    private Long id;
    private String nom;
    private String prenom;
    private List<ServiceDTO> service;

}

