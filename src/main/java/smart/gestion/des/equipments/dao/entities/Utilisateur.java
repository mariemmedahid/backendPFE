package smart.gestion.des.equipments.dao.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "utilisateurs")
public class Utilisateur {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        private String nom;
        private String motdepasse;
        private String email;
        private String telephone;
        private String imageUrl;
        private String token;

        @ManyToOne
        @JoinColumn(name = "role_id")
        private Role role;

        // Getters and Setters
}
