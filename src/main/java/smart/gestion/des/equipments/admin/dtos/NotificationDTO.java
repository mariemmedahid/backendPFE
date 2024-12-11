package smart.gestion.des.equipments.admin.dtos;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Setter
@Getter
public class NotificationDTO {
    private Long id;
    private String message;
    private UtilisateurDTO utilisateur;  // Use DTO to avoid recursion
    private EquipmentDTO equipment;  // Assuming you have an EquipmentDTO class
    private LocalDateTime date;

    // Getters and Setters (No need for annotations like @ManyToOne)
}
