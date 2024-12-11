package smart.gestion.des.equipments.dao.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import smart.gestion.des.equipments.dao.entities.Categorie;

@Repository
public interface CategorieRepository extends JpaRepository<Categorie, Long> {
}
