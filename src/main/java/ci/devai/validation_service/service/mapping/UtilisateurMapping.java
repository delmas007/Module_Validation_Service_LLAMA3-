package ci.devai.validation_service.service.mapping;

import ci.devai.validation_service.model.Utilisateur;
import ci.devai.validation_service.service.dto.ServiceDTO;
import ci.devai.validation_service.service.dto.UtilisateurDTO;

import java.util.stream.Collectors;

public class UtilisateurMapping {

    public static Utilisateur toEntity(UtilisateurDTO dto) {

        if (dto == null) {
            return null;
        }

        Utilisateur utilisateur = new Utilisateur();
        utilisateur.setId(dto.getId());
        utilisateur.setNom(dto.getNom());
        utilisateur.setPrenom(dto.getPrenom());
        if (dto.getService() != null) {
            utilisateur.setService(dto.getService().stream()
                    .map(ServiceMapping::toEntity)
                    .collect(Collectors.toList()));
        }
        return utilisateur;
    }

    public static UtilisateurDTO toDto(Utilisateur utilisateur) {

        if (utilisateur == null) {
            return null;
        }

        UtilisateurDTO dto = new UtilisateurDTO();
        dto.setId(utilisateur.getId());
        dto.setNom(utilisateur.getNom());
        dto.setPrenom(utilisateur.getPrenom());
        if (utilisateur.getService() != null) {
            dto.setService(utilisateur.getService().stream()
                    .map(ServiceMapping::toDto)
                    .collect(Collectors.toList()));
        }
        return dto;
    }
}
