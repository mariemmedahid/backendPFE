package smart.gestion.des.equipments.admin.services;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import smart.gestion.des.equipments.admin.dtos.CategorieDTO;
import smart.gestion.des.equipments.admin.dtos.EquipmentDTO;
import smart.gestion.des.equipments.admin.dtos.UtilisateurDTO;
import smart.gestion.des.equipments.dao.entities.Assignment;
import smart.gestion.des.equipments.dao.entities.Categorie;
import smart.gestion.des.equipments.dao.entities.Equipment;
import smart.gestion.des.equipments.dao.repositories.AssignmentRepository;
import smart.gestion.des.equipments.dao.repositories.CategorieRepository;
import smart.gestion.des.equipments.dao.repositories.EquipmentRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategorieService {

    @Autowired
    private CategorieRepository categorieRepository;

    @Autowired
    private EquipmentRepository equipmentRepository;

    @Autowired
    private AssignmentRepository assignmentRepository;
    @Autowired
    private ModelMapper modelMapper;

    public CategorieDTO createCategorie(CategorieDTO categorieDTO) {
        Categorie categorie = modelMapper.map(categorieDTO, Categorie.class);
        categorie = categorieRepository.save(categorie);
        return modelMapper.map(categorie, CategorieDTO.class);
    }

    public List<CategorieDTO> getAllCategories() {
        return categorieRepository.findAll()
                .stream()
                .map(categorie -> modelMapper.map(categorie, CategorieDTO.class))
                .collect(Collectors.toList());
    }

    public CategorieDTO getCategorieById(Long id) {
        Categorie categorie = categorieRepository.findById(id).orElse(null);
        return categorie != null ? modelMapper.map(categorie, CategorieDTO.class) : null;
    }

    public CategorieDTO updateCategorie(Long id, CategorieDTO categorieDTO) {
        Categorie categorie = categorieRepository.findById(id).orElse(null);
        if (categorie != null) {
            categorie.setNom(categorieDTO.getNom());
            categorie.setImage(categorieDTO.getImage());
            categorie = categorieRepository.save(categorie);
            return modelMapper.map(categorie, CategorieDTO.class);
        }
        return null;
    }

    public void deleteCategorie(Long id) {
        categorieRepository.deleteById(id);
    }

//    public List<EquipmentDTO> getEquipmentsByCategorie(Long categorieId) {
//        List<Equipment> equipments = equipmentRepository.findByCategorieId(categorieId);
//        return equipments.stream()
//                .map(equipment -> modelMapper.map(equipment, EquipmentDTO.class))
//                .collect(Collectors.toList());
//    }
public List<EquipmentDTO> getEquipmentsByCategorie(Long categorieId) {
    List<Equipment> equipments = equipmentRepository.findByCategorieId(categorieId);
    return equipments.stream()
            .map(equipment -> {
                EquipmentDTO dto = modelMapper.map(equipment, EquipmentDTO.class);
                // Fetch the associated user if necessary and check for null
                UtilisateurDTO utilisateurDTO = getUserByEquipment(equipment.getId()); // Ensure this method can return null
                if (utilisateurDTO != null) {
                    dto.setUtilisateur(utilisateurDTO);
                }
                return dto;
            })
            .collect(Collectors.toList());
}


    private UtilisateurDTO getUserByEquipment(Long equipmentId) {
        // Fetch the assignment related to the equipment
        Assignment assignment = assignmentRepository.findByEquipmentId(equipmentId);
        // Check if the assignment or utilisateur is null
        if (assignment != null && assignment.getUtilisateur() != null) {
            return modelMapper.map(assignment.getUtilisateur(), UtilisateurDTO.class);
        }
        return null; // Return null if no user is associated or the assignment doesn't exist
    }


}
