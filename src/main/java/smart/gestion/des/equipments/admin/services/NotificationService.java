package smart.gestion.des.equipments.admin.services;

import com.google.firebase.messaging.FirebaseMessagingException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import smart.gestion.des.equipments.admin.dtos.AssignmentDTO;
import smart.gestion.des.equipments.admin.dtos.EquipmentDTO;
import smart.gestion.des.equipments.admin.dtos.NotificationDTO;
import smart.gestion.des.equipments.admin.dtos.UtilisateurDTO;
import smart.gestion.des.equipments.dao.entities.Assignment;
import smart.gestion.des.equipments.dao.entities.Equipment;
import smart.gestion.des.equipments.dao.entities.Notification;
import smart.gestion.des.equipments.dao.entities.Utilisateur;
import smart.gestion.des.equipments.dao.repositories.NotificationRepository;
import smart.gestion.des.equipments.dao.repositories.UtilisateurRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

@Service
public class NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UtilisateurService utilisateurService;

    @Autowired
    private FirebaseMessagingService firebaseMessagingService;
    public NotificationDTO createNotif(NotificationDTO notificationDTO) throws ExecutionException, FirebaseMessagingException, InterruptedException {
        // Map AssignmentDTO to Assignment
        System.out.println("nottif "+notificationDTO.toString());
        Notification notif = modelMapper.map(notificationDTO, Notification.class);

        // Fetch and set the utilisateur (assuming utilisateur is mapped properly in AssignmentDTO)
        Utilisateur utilisateur = utilisateurRepository.findById(notificationDTO.getUtilisateur().getId())
                .orElseThrow(() -> new IllegalArgumentException("Utilisateur not found"));
        if (utilisateur != null && utilisateur.getToken() != null) {
            // Send notification via HTTP request
            firebaseMessagingService.sendNotificationToDevice(utilisateur.getToken(), notificationDTO.getMessage());
        }

        notif.setUtilisateur(utilisateur);
notif.setMessage(notificationDTO.getMessage());
        notif.setDate(LocalDateTime.now());        // Save assignment
        notif = notificationRepository.save(notif);

        // Map Assignment back to AssignmentDTO and return
        return modelMapper.map(notif, NotificationDTO.class);
    }


    public NotificationDTO createNotification(NotificationDTO notificationDTO) throws ExecutionException, FirebaseMessagingException, InterruptedException {
        // Fetch user and their token
        Utilisateur utilisateurr = utilisateurService.getUserById(notificationDTO.getUtilisateur().getId());

        if (utilisateurr != null && utilisateurr.getToken() != null) {
            // Send notification via HTTP request
            firebaseMessagingService.sendNotificationToDevice(utilisateurr.getToken(), notificationDTO.getMessage());
        }

        // Map DTO to Notification entity
        Notification notification = modelMapper.map(notificationDTO, Notification.class);

        // Set the creation date to now
        notification.setDate(LocalDateTime.now());

        // Fetch and set the user
        Optional<Utilisateur> utilisateur = utilisateurRepository.findById(notificationDTO.getUtilisateur().getId());
        utilisateur.ifPresent(notification::setUtilisateur);

        // Save the notification in the database
        notification = notificationRepository.save(notification);

        // Map the saved Notification entity back to DTO and return it
        return modelMapper.map(notification, NotificationDTO.class);
    }

    public List<NotificationDTO> getAllNotifications() {
        return notificationRepository.findAll()
                .stream()
                .map(notification -> modelMapper.map(notification, NotificationDTO.class))
                .collect(Collectors.toList());
    }
    public List<NotificationDTO> getAllNotif() {
        return notificationRepository.findAll()
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }
    private NotificationDTO convertToDto(Notification notif) {
        NotificationDTO notifDTO = modelMapper.map(notif, NotificationDTO.class);
        Utilisateur utilisateur = notif.getUtilisateur();
        Equipment equipment = notif.getEquipment();

        notifDTO.setUtilisateur(modelMapper.map(utilisateur, UtilisateurDTO.class));
        notifDTO.setEquipment(modelMapper.map(equipment, EquipmentDTO.class));

        return notifDTO;
    }


    public NotificationDTO getNotificationById(Long id) {
        return notificationRepository.findById(id)
                .map(notification -> modelMapper.map(notification, NotificationDTO.class))
                .orElse(null);
    }

    public NotificationDTO updateNotification(Long id, NotificationDTO notificationDTO) {
        Optional<Notification> optionalNotification = notificationRepository.findById(id);
        if (optionalNotification.isPresent()) {
            Notification notification = optionalNotification.get();
            notification.setMessage(notificationDTO.getMessage());
            notification.setDate(notificationDTO.getDate());

            // Fetch and set the user
            Optional<Utilisateur> utilisateur = utilisateurRepository.findById(notificationDTO.getUtilisateur().getId());
            utilisateur.ifPresent(notification::setUtilisateur);

            notification = notificationRepository.save(notification);
            return modelMapper.map(notification, NotificationDTO.class);
        }
        return null;
    }
    public List<NotificationDTO> getNotificationsByUtilisateurId(Long utilisateurId) {
        List<Notification> notifications = notificationRepository.findByUtilisateurId(utilisateurId);
        return notifications.stream()
                .map(notification -> modelMapper.map(notification, NotificationDTO.class))
                .collect(Collectors.toList());
    }
    public void deleteNotification(Long id) {
        notificationRepository.deleteById(id);
    }
}