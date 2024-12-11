package smart.gestion.des.equipments.admin.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EquipmentDTO {
    private Long id;
    private String nom;
    private String status;
    private String barcode;
    private String location;
    private String imageUrl;
    private CategorieDTO categorie;
    private UtilisateurDTO utilisateur; // Ensure this field is present

}
