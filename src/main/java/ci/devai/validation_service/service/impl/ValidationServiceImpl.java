package ci.devai.validation_service.service.impl;

import ci.devai.validation_service.Record.Service;
import ci.devai.validation_service.model.enume.ServiceStatus;
import ci.devai.validation_service.repository.ServiceRepository;
import ci.devai.validation_service.repository.UtilisateurRepository;
import ci.devai.validation_service.service.UtilisateurService;
import ci.devai.validation_service.service.ValidationService;
import ci.devai.validation_service.service.dto.ServiceDTO;
import ci.devai.validation_service.service.dto.StatutDTO;
import ci.devai.validation_service.service.dto.UtilisateurDTO;
import ci.devai.validation_service.service.mapping.ServiceMapping;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.ResponseEntity;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.core.ParameterizedTypeReference;

@org.springframework.stereotype.Service
public class ValidationServiceImpl implements ValidationService {
    private final ChatClient chatClient;
    private final ServiceRepository serviceRepository;
    private final UtilisateurService utilisateurService;

    public ValidationServiceImpl(ChatClient.Builder builder, ServiceRepository serviceRepository, UtilisateurService utilisateurService) {
        this.chatClient = builder.build();
        this.serviceRepository = serviceRepository;
        this.utilisateurService = utilisateurService;
    }
    @Override
    public StatutDTO validateService(Service service) {
//        String prompt =String.format(
//                """
//                Valide le service: %s  selon les critères suivants :
//                - Le typeService doit être l'un des types énumérés : DEVELOPPEMENT_WEB, DEVELOPPEMENT_MOBILE, DESIGN_GRAPHIQUE, MARKETING, FORMATION, PHOTOGRAPHIE.
//                - La description doit correspondre au typeService.
//                Par exemple :
//                * DEVELOPPEMENT_WEB doit inclure des termes liés aux sites web, applications web, développement backend ou frontend.
//                * DESIGN_GRAPHIQUE doit mentionner des termes liés au design visuel, création de logos, conception graphique.
//                * MARKETING doit inclure des références à la publicité, la stratégie marketing ou la gestion de campagnes.
//
//                Si le service ne respecte pas ces critères, indique "REFUSE" avec la cause spécifique.
//                Sinon, valide-le avec le statut "VALID".
//                """, service);
//
//        String systemMessage =
//                """
//                Tu es un validateur intelligent de services. Analyse le typeService et la description du service pour vérifier s'ils correspondent.
//                Retourne le service avec un statut "VALID" si les critères sont remplis, sinon "REFUSE" avec une cause claire.
//                """;

        String prompt = String.format(
                """
                Valide le service suivant :
                Type de service : %s 
                Description : %s
                Prix : %.2f
                Durée : %.2f jours
                ID utilisateur : %d
                
                Critères de validation :
                - Si la description ne correspond pas au type de service, retourne :
                  * status : "REFUSE"
                  * cause : Une explication claire de l'erreur.
                
                Exemples de correspondance correcte :
                - Type de service : "MARKETING" 
                  * Doit inclure : stratégie marketing, publicité, campagnes publicitaires, gestion de réseaux sociaux.
                - Type de service : "DEVELOPPEMENT_WEB"
                  * Doit inclure : développement de sites web, applications web, backend, frontend.
                - Type de service : "DESIGN_GRAPHIQUE"
                  * Doit inclure : conception graphique, logos, création visuelle, design UI/UX.
                
                Retour attendu :
                - Si tout est correct, retourne :
                  * status : "VALID"
                  * cause : null
                
                **Important** :
                - Retourne TOUS les champs fournis tels quels, y compris `typeService`, `description`, `price`, `duration`, et `id_utilisateur`.
                - Ne modifie PAS les champs autres que `status` et `cause`.
                - **Ne laisse JAMAIS `typeService` à `null`.**
                - **En cas de refus, renseigne TOUJOURS le champ `cause` avec une explication.**
                """,
                service.typeService(),
                service.description(),
                service.price(),
                service.duration(),
                service.id_utilisateur()
        );

        String systemMessage =
                """
                Tu es un validateur de services. 
                - Vérifie si la description correspond au type de service.
                - Si ce n'est pas le cas :
                  * Mets le statut `status` à "REFUSE".
                  * Fournis une explication claire dans le champ `cause`.
                - Si la validation est correcte :
                  * Mets le statut `status` à "VALID".
                  * Mets `cause` à `null`.
                
                **Rappels importants** :
                - **Ne laisse jamais le champ `typeService` à `null`.** 
                - Retourne TOUS les champs tels qu'ils ont été reçus (`typeService`, `description`, `price`, `duration`, `id_utilisateur`).
                - **Ne laisse jamais le champ `cause` à `null` si le statut est "REFUSE".**
                
                NB: en français les causes
                """;

        Service response = chatClient
                .prompt()
                .system(systemMessage)
                .user(prompt)
                .call()
                .entity(Service.class);

        ServiceDTO serviceDTO;
        StatutDTO statutDTO = new StatutDTO();
        serviceDTO = ServiceMapping.toDto(response);
        if (serviceDTO.getStatus() == ServiceStatus.VALID){
            UtilisateurDTO utilisateurDTO = utilisateurService.findByID(service.id_utilisateur());
            serviceDTO.setUtilisateur(utilisateurDTO);
            serviceRepository.save(ServiceMapping.toEntity(serviceDTO));
            statutDTO.setStatus(ServiceStatus.VALID);
            return statutDTO ;
        }else if (serviceDTO.getStatus() == ServiceStatus.REFUSE){
            statutDTO.setStatus(ServiceStatus.REFUSE);
            statutDTO.setCause(response.cause());
            return statutDTO;
        }
        return null;
    }

    @Override
    public Service validateServicee(Service service) {
        String prompt = String.format(
                """
                Valide le service suivant :
                Type de service : %s 
                Description : %s
                Prix : %.2f
                Durée : %.2f jours
                ID utilisateur : %d
                
                Critères de validation :
                - Si la description ne correspond pas au type de service, retourne :
                  * status : "REFUSE"
                  * cause : Une explication claire de l'erreur.
                
                Exemples de correspondance correcte :
                - Type de service : "MARKETING" 
                  * Doit inclure : stratégie marketing, publicité, campagnes publicitaires, gestion de réseaux sociaux.
                - Type de service : "DEVELOPPEMENT_WEB"
                  * Doit inclure : développement de sites web, applications web, backend, frontend.
                - Type de service : "DESIGN_GRAPHIQUE"
                  * Doit inclure : conception graphique, logos, création visuelle, design UI/UX.
                
                Retour attendu :
                - Si tout est correct, retourne :
                  * status : "VALID"
                  * cause : null
                
                **Important** :
                - Retourne TOUS les champs fournis tels quels, y compris `typeService`, `description`, `price`, `duration`, et `id_utilisateur`.
                - Ne modifie PAS les champs autres que `status` et `cause`.
                - **Ne laisse JAMAIS `typeService` à `null`.**
                - **En cas de refus, renseigne TOUJOURS le champ `cause` avec une explication.**
                """,
                service.typeService(),
                service.description(),
                service.price(),
                service.duration(),
                service.id_utilisateur()
        );

        String systemMessage =
                """
                Tu es un validateur de services. 
                - Vérifie si la description correspond au type de service.
                - Si ce n'est pas le cas :
                  * Mets le statut `status` à "REFUSE".
                  * Fournis une explication claire dans le champ `cause`.
                - Si la validation est correcte :
                  * Mets le statut `status` à "VALID".
                  * Mets `cause` à `null`.
                
                **Rappels importants** :
                - **Ne laisse jamais le champ `typeService` à `null`.** 
                - Retourne TOUS les champs tels qu'ils ont été reçus (`typeService`, `description`, `price`, `duration`, `id_utilisateur`).
                - **Ne laisse jamais le champ `cause` à `null` si le statut est "REFUSE".**
                """;


        Service response = chatClient
                .prompt()
                .system(systemMessage)
                .user(prompt)
                .call()
                .entity(Service.class);

        return response;
    }

}
