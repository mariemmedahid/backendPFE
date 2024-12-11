
package smart.gestion.des.equipments.dao.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import smart.gestion.des.equipments.dao.entities.Notification;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
    // Ajoutez ici des méthodes de requête personnalisées si nécessaire
    List<Notification> findByUtilisateurId(Long utilisateurId);

}

