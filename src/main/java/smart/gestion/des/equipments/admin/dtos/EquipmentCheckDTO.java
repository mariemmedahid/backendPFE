package smart.gestion.des.equipments.admin.dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class EquipmentCheckDTO {
    private Long id;
    private UtilisateurDTO utilisateur;
    private EquipmentDTO equipement;
    private Date checkDate;
    private String checkList;
    private String remarque;
}
