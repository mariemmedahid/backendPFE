package smart.gestion.des.equipments.admin.services;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import smart.gestion.des.equipments.admin.dtos.CategorieDTO;
import smart.gestion.des.equipments.admin.dtos.EquipmentDTO;
import smart.gestion.des.equipments.admin.dtos.RoleDTO;
import smart.gestion.des.equipments.admin.dtos.UtilisateurDTO;
import smart.gestion.des.equipments.dao.entities.*;
import smart.gestion.des.equipments.dao.repositories.AssignmentRepository;
import smart.gestion.des.equipments.dao.repositories.CategorieRepository;
import smart.gestion.des.equipments.dao.repositories.EquipmentRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
@Service
public class EquipmentService {

    @Autowired
    private EquipmentRepository equipmentRepository;

    @Autowired
    private CategorieRepository categorieRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private AssignmentRepository assignmentRepository;

    public EquipmentDTO createEquipment(EquipmentDTO equipmentDTO) {
        Equipment equipment = modelMapper.map(equipmentDTO, Equipment.class);

        // Set the category if it exists
        Optional<Categorie> categorie = categorieRepository.findById(equipmentDTO.getCategorie().getId());
        categorie.ifPresent(equipment::setCategorie);

        equipment = equipmentRepository.save(equipment);
        return mapToDto(equipment);
    }

    public List<EquipmentDTO> getAllEquipments() {
        return equipmentRepository.findAll()
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    public List<EquipmentDTO> getAllEquipmentWithUser() {
        return equipmentRepository.findAll().stream().map(equipment -> {
            // Check if the equipment has an assignment
            Optional<Assignment> assignment = Optional.ofNullable(assignmentRepository.findByEquipmentId(equipment.getId()));

            // If an assignment exists, map the associated user to UtilisateurDTO
            UtilisateurDTO utilisateurDTO = assignment.map(a -> mapToUtilisateurDTO(a.getUtilisateur())).orElse(null);

            // Map the equipment and set the associated user
            EquipmentDTO equipmentDTO = mapToDto(equipment);
            equipmentDTO.setUtilisateur(utilisateurDTO);
            return equipmentDTO;
        }).collect(Collectors.toList());
    }

    public List<EquipmentDTO> getEquipmentsByUserId(Long userId) {
        return assignmentRepository.findByUtilisateurId(userId).stream()
                .map(assignment -> mapToDto(assignment.getEquipment()))
                .collect(Collectors.toList());
    }

    private UtilisateurDTO mapToUtilisateurDTO(Utilisateur utilisateur) {
        UtilisateurDTO dto = new UtilisateurDTO();
        dto.setId(utilisateur.getId());
        dto.setNom(utilisateur.getNom());
        dto.setMotdepasse(utilisateur.getMotdepasse());
        dto.setEmail(utilisateur.getEmail());
        dto.setTelephone(utilisateur.getTelephone());
        dto.setImageUrl(utilisateur.getImageUrl());
        dto.setToken(utilisateur.getToken());
        dto.setRole(mapToRoleDto(utilisateur.getRole()));
        return dto;
    }
    private RoleDTO mapToRoleDto(Role role){
        RoleDTO dto= new RoleDTO();
        dto.setNom(role.getNom());
        dto.setId(role.getId());
        return  dto;

    }

    public EquipmentDTO getEquipmentById(Long id) {
        return equipmentRepository.findById(id)
                .map(this::mapToDto)
                .orElse(null);
    }

    public EquipmentDTO updateEquipment(Long id, EquipmentDTO equipmentDTO) {
        Optional<Equipment> optionalEquipment = equipmentRepository.findById(id);
        if (optionalEquipment.isPresent()) {
            Equipment equipment = optionalEquipment.get();
            equipment.setNom(equipmentDTO.getNom());
            equipment.setStatus(equipmentDTO.getStatus());
            equipment.setBarcode(equipmentDTO.getBarcode());
            equipment.setLocation(equipmentDTO.getLocation());
            equipment.setImageUrl(equipmentDTO.getImageUrl());

            // Set the category if it exists
            Optional<Categorie> categorie = categorieRepository.findById(equipmentDTO.getCategorie().getId());
            categorie.ifPresent(equipment::setCategorie);

            equipment = equipmentRepository.save(equipment);
            return mapToDto(equipment);
        }
        return null;
    }

    public void deleteEquipment(Long id) {
        equipmentRepository.deleteById(id);
    }

    private EquipmentDTO mapToDto(Equipment equipment) {
        EquipmentDTO equipmentDTO = modelMapper.map(equipment, EquipmentDTO.class);
        CategorieDTO categorieDTO = modelMapper.map(equipment.getCategorie(), CategorieDTO.class);
        equipmentDTO.setCategorie(categorieDTO);

        // Map UtilisateurDTO if assignment exists
        Assignment assignment = assignmentRepository.findByEquipmentId(equipment.getId());
        if (assignment != null && assignment.getUtilisateur() != null) {
            UtilisateurDTO utilisateurDTO = mapToUtilisateurDTO(assignment.getUtilisateur());
            equipmentDTO.setUtilisateur(utilisateurDTO);
        }

        return equipmentDTO;
    }
}
