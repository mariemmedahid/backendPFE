package smart.gestion.des.equipments.dao.repositories;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import smart.gestion.des.equipments.dao.entities.Assignment;

import java.util.List;

public interface AssignmentRepository extends JpaRepository<Assignment, Long> {
    @Transactional
    void deleteByUtilisateurIdAndEquipmentId(Long utilisateurId, Long equipmentId);

    @Query("SELECT a FROM Assignment a WHERE a.utilisateur.id = :userId")
    List<Assignment> findAllByUtilisateurId(Long userId);

    List<Assignment> findByUtilisateurId(Long utilisateurId);
    Assignment findByEquipmentId(Long equipmentId);


}
