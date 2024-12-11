package smart.gestion.des.equipments.dao.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import smart.gestion.des.equipments.dao.entities.Probleme;

import java.util.List;

@Repository
public interface ProblemeRepository extends JpaRepository<Probleme, Long> {
    List<Probleme> findByUtilisateurId(Long utilisateurId);
}
