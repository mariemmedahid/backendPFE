package smart.gestion.des.equipments.dao.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import smart.gestion.des.equipments.dao.entities.Equipment;

import java.util.List;

@Repository
public interface EquipmentRepository extends JpaRepository<Equipment, Long> {
    List<Equipment> findByCategorieId(Long categorieId);
    long countByStatus(String status);
}
