package smart.gestion.des.equipments.admin.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DemandeDTO {
    private Long id;
    private UtilisateurDTO utilisateur;  // Using UtilisateurDTO instead of the full entity
    private NotificationDTO notification;  // Using NotificationDTO to avoid complex objects
    private String motivation;

    // Getters and Setters
}
