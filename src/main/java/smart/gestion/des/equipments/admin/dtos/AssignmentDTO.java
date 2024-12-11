package smart.gestion.des.equipments.admin.dtos;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Date;

@Getter
@Setter
public class AssignmentDTO {
    private Long id;
    @ManyToOne(cascade = CascadeType.REMOVE)
    private UtilisateurDTO utilisateur;
    private EquipmentDTO equipment;
    private Long date;
}
