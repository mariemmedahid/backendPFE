package smart.gestion.des.equipments.admin.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProblemeDTO {
    private Long id;
    private Long utilisateurId;
    private Long equipmentId;
    private String description;


}
