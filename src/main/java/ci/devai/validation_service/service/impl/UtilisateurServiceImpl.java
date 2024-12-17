package ci.devai.validation_service.service.impl;

import ci.devai.validation_service.repository.UtilisateurRepository;
import ci.devai.validation_service.service.UtilisateurService;
import ci.devai.validation_service.service.dto.UtilisateurDTO;
import ci.devai.validation_service.service.mapping.UtilisateurMapping;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UtilisateurServiceImpl implements UtilisateurService {
    private final UtilisateurRepository utilisateurRepository;

    @Override
    public UtilisateurDTO findByID(Long id) {
        return utilisateurRepository.findById(id).stream().map(UtilisateurMapping::toDto).findFirst().orElse(null);
    }
}
