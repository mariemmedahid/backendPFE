package smart.gestion.des.equipments.dao.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "equipments")
public class Equipment {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;
        private String nom;
        private String status;
        private String barcode;
        private String location;
        private String imageUrl;

        @ManyToOne
        @JoinColumn(name = "categorie_id")
        private Categorie categorie;

        @ManyToOne
        @JoinColumn(name = "utilisateur_id")
        private Utilisateur utilisateur;

        // getters and setters
}
