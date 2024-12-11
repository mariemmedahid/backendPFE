package smart.gestion.des.equipments.admin.controllers;

import com.google.firebase.messaging.FirebaseMessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import smart.gestion.des.equipments.admin.dtos.NotificationDTO;
import smart.gestion.des.equipments.admin.services.NotificationService;

import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;
    @CrossOrigin(origins = "*")
    @PostMapping
    public ResponseEntity<NotificationDTO> createNotification(@RequestBody NotificationDTO notificationDTO) throws ExecutionException, FirebaseMessagingException, InterruptedException {
        NotificationDTO createdNotification = notificationService.createNotif(notificationDTO);
        return ResponseEntity.ok(createdNotification);
    }
    @CrossOrigin(origins = "*")
    @GetMapping
    public ResponseEntity<List<NotificationDTO>> getAllNotifications() {
        List<NotificationDTO> notifications = notificationService.getAllNotif();
        return ResponseEntity.ok(notifications);
    }
    @CrossOrigin(origins = "*")
    @GetMapping("/{id}")
    public ResponseEntity<NotificationDTO> getNotificationById(@PathVariable Long id) {
        NotificationDTO notification = notificationService.getNotificationById(id);
        return notification != null ? ResponseEntity.ok(notification) : ResponseEntity.notFound().build();
    }
    @CrossOrigin(origins = "*")
    @PutMapping("/{id}")
    public ResponseEntity<NotificationDTO> updateNotification(@PathVariable Long id, @RequestBody NotificationDTO notificationDTO) {
        NotificationDTO updatedNotification = notificationService.updateNotification(id, notificationDTO);
        return updatedNotification != null ? ResponseEntity.ok(updatedNotification) : ResponseEntity.notFound().build();
    }
    @CrossOrigin(origins = "*")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNotification(@PathVariable Long id) {
        notificationService.deleteNotification(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/user/{utilisateurId}")
    public ResponseEntity<List<NotificationDTO>> getNotificationsByUtilisateurId(@PathVariable Long utilisateurId) {
        List<NotificationDTO> notifications = notificationService.getNotificationsByUtilisateurId(utilisateurId);
        return ResponseEntity.ok(notifications);
    }
}