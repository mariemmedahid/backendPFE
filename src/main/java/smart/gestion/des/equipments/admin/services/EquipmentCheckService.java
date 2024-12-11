package smart.gestion.des.equipments.admin.services;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import smart.gestion.des.equipments.admin.dtos.EquipmentCheckDTO;
import smart.gestion.des.equipments.admin.dtos.UtilisateurDTO;
import smart.gestion.des.equipments.admin.dtos.EquipmentDTO;
import smart.gestion.des.equipments.dao.entities.EquipmentCheck;
import smart.gestion.des.equipments.dao.entities.Utilisateur;
import smart.gestion.des.equipments.dao.entities.Equipment;
import smart.gestion.des.equipments.dao.repositories.EquipmentCheckRepository;
import smart.gestion.des.equipments.dao.repositories.UtilisateurRepository;
import smart.gestion.des.equipments.dao.repositories.EquipmentRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EquipmentCheckService {

    @Autowired
    private EquipmentCheckRepository equipmentCheckRepository;

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    @Autowired
    private EquipmentRepository equipementRepository;

    // Create a new equipment check
    public EquipmentCheckDTO createEquipmentCheck(EquipmentCheckDTO equipmentCheckDTO) {
        EquipmentCheck equipmentCheck = new EquipmentCheck();

        // Set Utilisateur
        Utilisateur utilisateur = utilisateurRepository.findById(equipmentCheckDTO.getUtilisateur().getId())
                .orElseThrow(() -> new EntityNotFoundException("Utilisateur not found with ID: " + equipmentCheckDTO.getUtilisateur().getId()));
        equipmentCheck.setUtilisateur(utilisateur);

        // Set Equipement
        Equipment equipement = equipementRepository.findById(equipmentCheckDTO.getEquipement().getId())
                .orElseThrow(() -> new EntityNotFoundException("Equipement not found with ID: " + equipmentCheckDTO.getEquipement().getId()));
        equipmentCheck.setEquipement(equipement);

        equipmentCheck.setCheckDate(equipmentCheckDTO.getCheckDate());
        equipmentCheck.setCheckList(equipmentCheckDTO.getCheckList());
        equipmentCheck.setRemarque(equipmentCheckDTO.getRemarque());

        equipmentCheck = equipmentCheckRepository.save(equipmentCheck);

        return mapToDTO(equipmentCheck);
    }

    // Read - Find all
    public List<EquipmentCheckDTO> getAllEquipmentChecks() {
        List<EquipmentCheck> equipmentChecks = equipmentCheckRepository.findAll();
        return equipmentChecks.stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    // Read - Find by id
    public Optional<EquipmentCheckDTO> getEquipmentCheckById(Long id) {
        return equipmentCheckRepository.findById(id).map(this::mapToDTO);
    }

    // Update
    public EquipmentCheckDTO updateEquipmentCheck(Long id, EquipmentCheckDTO equipmentCheckDTO) {
        EquipmentCheck equipmentCheck = equipmentCheckRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("EquipmentCheck not found with ID: " + id));

        // Set Utilisateur
        Utilisateur utilisateur = utilisateurRepository.findById(equipmentCheckDTO.getUtilisateur().getId())
                .orElseThrow(() -> new EntityNotFoundException("Utilisateur not found with ID: " + equipmentCheckDTO.getUtilisateur().getId()));
        equipmentCheck.setUtilisateur(utilisateur);

        // Set Equipement
        Equipment equipement = equipementRepository.findById(equipmentCheckDTO.getEquipement().getId())
                .orElseThrow(() -> new EntityNotFoundException("Equipement not found with ID: " + equipmentCheckDTO.getEquipement().getId()));
        equipmentCheck.setEquipement(equipement);

        equipmentCheck.setCheckDate(equipmentCheckDTO.getCheckDate());
        equipmentCheck.setCheckList(equipmentCheckDTO.getCheckList());
        equipmentCheck.setRemarque(equipmentCheckDTO.getRemarque());

        equipmentCheck = equipmentCheckRepository.save(equipmentCheck);

        return mapToDTO(equipmentCheck);
    }

    // Delete
    public void deleteEquipmentCheck(Long id) {
        equipmentCheckRepository.deleteById(id);
    }

    // Map EquipmentCheck to EquipmentCheckDTO
    private EquipmentCheckDTO mapToDTO(EquipmentCheck equipmentCheck) {
        EquipmentCheckDTO dto = new EquipmentCheckDTO();
        dto.setId(equipmentCheck.getId());

        // Set UtilisateurDTO
        UtilisateurDTO utilisateurDTO = new UtilisateurDTO();
        utilisateurDTO.setId(equipmentCheck.getUtilisateur().getId());
        utilisateurDTO.setNom(equipmentCheck.getUtilisateur().getNom());
        utilisateurDTO.setEmail(equipmentCheck.getUtilisateur().getEmail());
        utilisateurDTO.setTelephone(equipmentCheck.getUtilisateur().getTelephone());
        dto.setUtilisateur(utilisateurDTO);

        // Set EquipementDTO
        EquipmentDTO equipementDTO = new EquipmentDTO();
        equipementDTO.setId(equipmentCheck.getEquipement().getId());
        equipementDTO.setNom(equipmentCheck.getEquipement().getNom());
        equipementDTO.setStatus(equipmentCheck.getEquipement().getStatus());
        equipementDTO.setLocation(equipmentCheck.getEquipement().getLocation());
        dto.setEquipement(equipementDTO);

        dto.setCheckDate(equipmentCheck.getCheckDate());
        dto.setCheckList(equipmentCheck.getCheckList());
        dto.setRemarque(equipmentCheck.getRemarque());

        return dto;
    }
}
