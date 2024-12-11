package smart.gestion.des.equipments.dao.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "demandes")
public class Demande {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @ManyToOne
        @JoinColumn(name = "utilisateur_id", nullable = false)
        private Utilisateur utilisateur;

        @ManyToOne
        @JoinColumn(name = "notification_id", nullable = false)
        private Notification notification;

        private String motivation;  //
}
