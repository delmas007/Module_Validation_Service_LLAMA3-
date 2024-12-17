package ci.devai.validation_service.service.mapping;

import ci.devai.validation_service.model.Service;
import ci.devai.validation_service.service.dto.ServiceDTO;
import ci.devai.validation_service.service.dto.UtilisateurDTO;

public class ServiceMapping {

    public static Service toEntity(ServiceDTO dto) {

        if (dto == null) {
            return null;
        }

        Service service = new Service();
        service.setId(dto.getId());
        service.setTypeService(dto.getTypeService());
        service.setStatus(dto.getStatus());
        service.setDescription(dto.getDescription());
        service.setPrice(dto.getPrice());
        service.setDuration(dto.getDuration());
        if (dto.getUtilisateur() != null) {
            service.setUtilisateur(UtilisateurMapping.toEntity(dto.getUtilisateur()));
        }
        return service;
    }

    public static ServiceDTO toDto(Service service) {
        ServiceDTO dto = new ServiceDTO();
        dto.setId(service.getId());
        dto.setTypeService(service.getTypeService());
        dto.setStatus(service.getStatus());
        dto.setDescription(service.getDescription());
        dto.setPrice(service.getPrice());
        dto.setDuration(service.getDuration());
        if (service.getUtilisateur() != null) {
            dto.setUtilisateur(UtilisateurMapping.toDto(service.getUtilisateur()));
        }
        return dto;
    }

    public static ServiceDTO toDto(ci.devai.validation_service.Record.Service service) {
        ServiceDTO dto = new ServiceDTO();
        dto.setTypeService(service.typeService());
        dto.setStatus(service.status());
        dto.setDescription(service.description());
        dto.setPrice(service.price());
        dto.setDuration(service.duration());
        dto.setCause(service.cause());
        return dto;
    }
}
