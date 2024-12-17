package ci.devai.validation_service.model;

import ci.devai.validation_service.model.enume.ServiceStatus;
import ci.devai.validation_service.model.enume.TypeService;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table
public class Service {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    private TypeService typeService;
    @Enumerated(EnumType.STRING)
    private ServiceStatus status;
    private String description;
    private Float price;
    private Float duration;
    private String cause;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "utilisateur_id")
    private Utilisateur utilisateur;
}
