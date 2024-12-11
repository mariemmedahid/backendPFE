package smart.gestion.des.equipments.admin.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UtilisateurDTO {
    private Long id;
    private String nom;
    private String motdepasse;
    private String email;
    private String telephone;
    private String imageUrl;
    private String token;
    private RoleDTO role;  // Instead of a full entity, use RoleDTO to avoid recursion

    // Getters and Setters
}
