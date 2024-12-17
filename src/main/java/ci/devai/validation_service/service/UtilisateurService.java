package ci.devai.validation_service.service;

import ci.devai.validation_service.service.dto.UtilisateurDTO;

public interface UtilisateurService {
    UtilisateurDTO findByID(Long id);
}
