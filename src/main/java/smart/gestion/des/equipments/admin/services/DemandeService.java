package smart.gestion.des.equipments.admin.services;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import smart.gestion.des.equipments.admin.dtos.DemandeDTO;
import smart.gestion.des.equipments.admin.dtos.NotificationDTO;
import smart.gestion.des.equipments.admin.dtos.UtilisateurDTO;
import smart.gestion.des.equipments.dao.entities.Demande;
import smart.gestion.des.equipments.dao.entities.Notification;
import smart.gestion.des.equipments.dao.entities.Utilisateur;
import smart.gestion.des.equipments.dao.repositories.DemandeRepository;
import smart.gestion.des.equipments.dao.repositories.NotificationRepository;
import smart.gestion.des.equipments.dao.repositories.UtilisateurRepository;

import java.util.List;
import java.util.Optional;

@Service
public class DemandeService {

    @Autowired
    private DemandeRepository demandeRepository;

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    // Create a new demande from DemandeDTO
    public Demande createDemande(DemandeDTO demandeDTO) {
        Demande demande = new Demande();

        // Fetch Utilisateur entity using the ID from DemandeDTO
        Utilisateur utilisateur = utilisateurRepository.findById(demandeDTO.getUtilisateur().getId())
                .orElseThrow(() -> new EntityNotFoundException("Utilisateur not found with ID: " + demandeDTO.getUtilisateur().getId()));
        demande.setUtilisateur(utilisateur);

        // Fetch Notification entity using the ID from DemandeDTO
        Notification notification = notificationRepository.findById(demandeDTO.getNotification().getId())
                .orElseThrow(() -> new EntityNotFoundException("Notification not found with ID: " + demandeDTO.getNotification().getId()));
        demande.setNotification(notification);

        demande.setMotivation(demandeDTO.getMotivation());

        return demandeRepository.save(demande);
    }

    // Read - Find all demandes
    public List<Demande> getAllDemandes() {
        return demandeRepository.findAll();
    }

    // Read - Find demande by id
    public Optional<Demande> getDemandeById(Long id) {
        return demandeRepository.findById(id);
    }

    // Update demande
    public Demande updateDemande(Long id, DemandeDTO demandeDTO) {
        return demandeRepository.findById(id)
                .map(demande -> {
                    // Fetch and update Utilisateur if needed
                    Utilisateur utilisateur = utilisateurRepository.findById(demandeDTO.getUtilisateur().getId())
                            .orElseThrow(() -> new EntityNotFoundException("Utilisateur not found with ID: " + demandeDTO.getUtilisateur().getId()));
                    demande.setUtilisateur(utilisateur);

                    // Fetch and update Notification if needed
                    Notification notification = notificationRepository.findById(demandeDTO.getNotification().getId())
                            .orElseThrow(() -> new EntityNotFoundException("Notification not found with ID: " + demandeDTO.getNotification().getId()));
                    demande.setNotification(notification);

                    // Update motivation
                    demande.setMotivation(demandeDTO.getMotivation());

                    return demandeRepository.save(demande);
                })
                .orElseThrow(() -> new RuntimeException("Demande not found with ID: " + id));
    }

    // Delete demande by id
    public void deleteDemande(Long id) {
        demandeRepository.deleteById(id);
    }
}
