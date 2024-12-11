package smart.gestion.des.equipments.dao.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "equipment_checks")
public class EquipmentCheck {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        // Add foreign key column for equipment
        @ManyToOne
        @JoinColumn(name = "id_equipement", nullable = false)
        private Equipment equipement;

        // Add foreign key column for user
        @ManyToOne
        @JoinColumn(name = "id_utilisateur", nullable = false)
        private Utilisateur utilisateur;

        @Temporal(TemporalType.DATE)
        private Date checkDate;

        private String checkList;

        private String remarque;

        // Getters and Setters (provided by Lombok)
}
