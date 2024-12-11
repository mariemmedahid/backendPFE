
package smart.gestion.des.equipments.admin.services;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import smart.gestion.des.equipments.admin.dtos.AssignmentDTO;
import smart.gestion.des.equipments.admin.dtos.CategorieDTO;
import smart.gestion.des.equipments.admin.dtos.EquipmentDTO;
import smart.gestion.des.equipments.admin.dtos.UtilisateurDTO;
import smart.gestion.des.equipments.dao.entities.Assignment;
import smart.gestion.des.equipments.dao.entities.Categorie;
import smart.gestion.des.equipments.dao.entities.Equipment;
import smart.gestion.des.equipments.dao.entities.Utilisateur;
import smart.gestion.des.equipments.dao.repositories.AssignmentRepository;
import smart.gestion.des.equipments.dao.repositories.EquipmentRepository;
import smart.gestion.des.equipments.dao.repositories.UtilisateurRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AssignmentService {

    @Autowired
    private AssignmentRepository assignmentRepository;

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    @Autowired
    private EquipmentRepository equipmentRepository;

    @Autowired
    private ModelMapper modelMapper;

    public AssignmentDTO createAssignment(AssignmentDTO assignmentDTO) {
        // Map AssignmentDTO to Assignment
        Assignment assignment = modelMapper.map(assignmentDTO, Assignment.class);

        // Fetch and set the utilisateur (assuming utilisateur is mapped properly in AssignmentDTO)
        Utilisateur utilisateur = utilisateurRepository.findById(assignmentDTO.getUtilisateur().getId())
                .orElseThrow(() -> new IllegalArgumentException("Utilisateur not found"));
        assignment.setUtilisateur(utilisateur);

        // Save assignment
        assignment = assignmentRepository.save(assignment);

        // Map Assignment back to AssignmentDTO and return
        return modelMapper.map(assignment, AssignmentDTO.class);
    }
    public List<AssignmentDTO> getAllAssignments() {
        return assignmentRepository.findAll()
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    private AssignmentDTO convertToDto(Assignment assignment) {
        AssignmentDTO assignmentDTO = modelMapper.map(assignment, AssignmentDTO.class);
        Utilisateur utilisateur = assignment.getUtilisateur();
        Equipment equipment = assignment.getEquipment();

        assignmentDTO.setUtilisateur(modelMapper.map(utilisateur, UtilisateurDTO.class));
        assignmentDTO.setEquipment(modelMapper.map(equipment, EquipmentDTO.class));

        return assignmentDTO;
    }

    public AssignmentDTO getAssignmentById(Long id) {
        return assignmentRepository.findById(id)
                .map(assignment -> modelMapper.map(assignment, AssignmentDTO.class))
                .orElse(null);
    }

    public AssignmentDTO updateAssignment(Long id, AssignmentDTO assignmentDTO) {
        return assignmentRepository.findById(id)
                .map(existingAssignment -> {
                    existingAssignment.setDate(assignmentDTO.getDate());


                    assignmentRepository.save(existingAssignment);
                    return modelMapper.map(existingAssignment, AssignmentDTO.class);
                })
                .orElse(null);
    }


    public List<EquipmentDTO> getAllEquipmentByUserId(Long userId) {
        List<Assignment> assignments = assignmentRepository.findAllByUtilisateurId(userId);
        return assignments.stream()
                .map(assignment -> EquipmnttoDTO(assignment.getEquipment()))
                .collect(Collectors.toList());
    }

    private static CategorieDTO
    CatToDTO(Categorie categorie) {
        CategorieDTO dto = new CategorieDTO();
        dto.setId(categorie.getId());
        dto.setNom(categorie.getNom());
        // Assuming date is a LocalDate
        return dto;
    }

    public static UtilisateurDTO UsertoDTO(Utilisateur utilisateur) {
        if (utilisateur == null) {
            return null;
        }

        UtilisateurDTO dto = new UtilisateurDTO();
        dto.setId(utilisateur.getId());
        dto.setEmail(utilisateur.getEmail());
        dto.setNom(utilisateur.getNom());


        // Map other fields as necessary

        return dto;
    }

    public static EquipmentDTO EquipmnttoDTO(Equipment equipment) {
        if (equipment == null) {
            return null;
        }

        EquipmentDTO dto = new EquipmentDTO();
        dto.setId(equipment.getId());
        dto.setNom(equipment.getNom());
        dto.setStatus(equipment.getStatus());
        dto.setBarcode(equipment.getBarcode());
        dto.setLocation(equipment.getLocation());
        dto.setImageUrl(equipment.getImageUrl());
        dto.setCategorie(CatToDTO(equipment.getCategorie()));

        return dto;
    }

    public void deleteAssignment(Long id) {
        assignmentRepository.deleteById(id);
    }

    public void removeAssignment(Long userId, Long equipmentId) {
        assignmentRepository.deleteByUtilisateurIdAndEquipmentId(userId, equipmentId);
    }


    public List<EquipmentDTO> getEquipmentsByUserId(Long userId) {
        List<Assignment> assignments = assignmentRepository.findByUtilisateurId(userId);

        return assignments.stream()
                .map(assignment -> modelMapper.map(assignment.getEquipment(), EquipmentDTO.class))
                .collect(Collectors.toList());
    }


}




