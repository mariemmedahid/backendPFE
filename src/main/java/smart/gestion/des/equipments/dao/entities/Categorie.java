package smart.gestion.des.equipments.dao.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;
@Getter
@Setter
@Entity
@Table(name = "categories")
public class Categorie {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        private String nom;
        private  String image;

        @OneToMany(mappedBy = "categorie")
        private Set<Equipment> equipments;

        // Getters and Setters
}
