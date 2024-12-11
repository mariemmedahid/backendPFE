package smart.gestion.des.equipments.admin.services;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import smart.gestion.des.equipments.admin.dtos.RoleDTO;
import smart.gestion.des.equipments.admin.dtos.UtilisateurDTO;
import smart.gestion.des.equipments.dao.entities.Assignment;
import smart.gestion.des.equipments.dao.entities.Role;
import smart.gestion.des.equipments.dao.entities.Utilisateur;
import smart.gestion.des.equipments.dao.repositories.AssignmentRepository;
import smart.gestion.des.equipments.dao.repositories.RoleRepository;
import smart.gestion.des.equipments.dao.repositories.UtilisateurRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class UtilisateurService {

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private AssignmentRepository assignmentRepository;
    @Autowired
    private ModelMapper modelMapper;

    public UtilisateurDTO createUtilisateur(UtilisateurDTO utilisateurDTO) {
        Utilisateur utilisateur = modelMapper.map(utilisateurDTO, Utilisateur.class);

        // Fetch and set the role
        Optional<Role> role = roleRepository.findById(utilisateurDTO.getRole().getId());
        role.ifPresent(utilisateur::setRole);

        utilisateur = utilisateurRepository.save(utilisateur);
        return modelMapper.map(utilisateur, UtilisateurDTO.class);
    }

    public List<UtilisateurDTO> getAllUtilisateurs() {
        return utilisateurRepository.findAll()
                .stream()
                .map(utilisateur -> {
                    UtilisateurDTO utilisateurDTO = modelMapper.map(utilisateur, UtilisateurDTO.class);
                    RoleDTO roleDTO = modelMapper.map(utilisateur.getRole(), RoleDTO.class);
                    utilisateurDTO.setRole(roleDTO);
                    return utilisateurDTO;
                })
                .collect(Collectors.toList());
    }

    public UtilisateurDTO getUtilisateurById(Long id) {
        return utilisateurRepository.findById(id)
                .map(utilisateur -> modelMapper.map(utilisateur, UtilisateurDTO.class))
                .orElse(null);
    }

    public UtilisateurDTO updateUtilisateur(Long id, UtilisateurDTO utilisateurDTO) {
        Optional<Utilisateur> optionalUtilisateur = utilisateurRepository.findById(id);
        if (optionalUtilisateur.isPresent()) {
            Utilisateur utilisateur = optionalUtilisateur.get();
            utilisateur.setNom(utilisateurDTO.getNom());
            utilisateur.setMotdepasse(utilisateurDTO.getMotdepasse());
            utilisateur.setEmail(utilisateurDTO.getEmail());  // Set email
            utilisateur.setTelephone(utilisateurDTO.getTelephone());  // Set telephone
            utilisateur.setImageUrl(utilisateurDTO.getImageUrl());  // Set image URL

            // Fetch and set the role
            Optional<Role> role = roleRepository.findById(utilisateurDTO.getRole().getId());
            role.ifPresent(utilisateur::setRole);

            utilisateur = utilisateurRepository.save(utilisateur);
            return modelMapper.map(utilisateur, UtilisateurDTO.class);
        }
        return null;
    }

    public void deleteUtilisateur(Long id) {
        // Find the user by id
        Utilisateur utilisateur = utilisateurRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Utilisateur not found with id: " + id));

        // Delete all assignments associated with the user
        List<Assignment> assignments = assignmentRepository.findByUtilisateurId(id);
        assignmentRepository.deleteAll(assignments);

        // Delete the user
        utilisateurRepository.delete(utilisateur);
    }
    public void deleteUtilfisateur(Long id) {
        // Find the user by id
        Utilisateur utilisateur = utilisateurRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Utilisateur not found with id: " + id));

        // Delete all assignments associated with the user
        List<Assignment> assignments = assignmentRepository.findByUtilisateurId(id);
        assignmentRepository.deleteAll(assignments);

        // Delete the user
        utilisateurRepository.delete(utilisateur);
    }

    public Optional<Utilisateur> findByEmail(String email) {
        return utilisateurRepository.findByEmail(email);
    }

    public UtilisateurDTO authenticate(String email, String password) {
        Optional<Utilisateur> userOpt = findByEmail(email);
        if (userOpt.isPresent()) {
            Utilisateur user = userOpt.get();
            // For simplicity, assuming password validation is straightforward (not encrypted)
            if (user.getMotdepasse().equals(password)) {
                return modelMapper.map(user, UtilisateurDTO.class); // Return full user data including password
            }
        }
        return null;
    }
    @Transactional
    public Utilisateur updateToken(Long utilisateurId, String token) {
        Utilisateur utilisateur = utilisateurRepository.findById(utilisateurId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        utilisateur.setToken(token); // Update the token
        return utilisateurRepository.save(utilisateur); // Save the updated user
    }
    public Utilisateur getUserById(Long id) {
        Optional<Utilisateur> utilisateurOptional = utilisateurRepository.findById(id);
        return utilisateurOptional.orElse(null); // Returns null if user not found
    }
}
