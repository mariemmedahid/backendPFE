package smart.gestion.des.equipments.dao.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import smart.gestion.des.equipments.dao.entities.Demande;

public interface DemandeRepository extends JpaRepository<Demande, Long> {
}
