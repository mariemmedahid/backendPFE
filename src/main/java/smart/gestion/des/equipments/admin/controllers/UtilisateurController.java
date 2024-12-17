package smart.gestion.des.equipments.admin.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import smart.gestion.des.equipments.admin.dtos.UtilisateurDTO;
import smart.gestion.des.equipments.admin.services.UtilisateurService;
import smart.gestion.des.equipments.dao.entities.Utilisateur;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/utilisateurs")
public class UtilisateurController {

    @Autowired
    private UtilisateurService utilisateurService;

    @Autowired
    private ModelMapper modelMapper; // Inject ModelMapper

    @CrossOrigin(origins = "*")
    @PostMapping
    public ResponseEntity<UtilisateurDTO> createUtilisateur(@RequestBody UtilisateurDTO utilisateurDTO) {
        UtilisateurDTO createdUtilisateur = utilisateurService.createUtilisateur(utilisateurDTO);
        return ResponseEntity.ok(createdUtilisateur);
    }
    @CrossOrigin(origins = "*")

    @GetMapping
    public ResponseEntity<List<UtilisateurDTO>> getAllUtilisateurs() {
        List<UtilisateurDTO> utilisateurs = utilisateurService.getAllUtilisateurs();
        return ResponseEntity.ok(utilisateurs);
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/{id}")
    public ResponseEntity<UtilisateurDTO> getUtilisateurById(@PathVariable Long id) {
        UtilisateurDTO utilisateur = utilisateurService.getUtilisateurById(id);
        return utilisateur != null ? ResponseEntity.ok(utilisateur) : ResponseEntity.notFound().build();
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<UtilisateurDTO> getUtilisateurByEmail(@PathVariable String email) {
        UtilisateurDTO utilisateur = utilisateurService.getUtilisateurByEmail(email);
        return utilisateur != null ? ResponseEntity.ok(utilisateur) : ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<UtilisateurDTO> updateUtilisateur(@PathVariable Long id, @RequestBody UtilisateurDTO utilisateurDTO) {
        UtilisateurDTO updatedUtilisateur = utilisateurService.updateUtilisateur(id, utilisateurDTO);
        return updatedUtilisateur != null ? ResponseEntity.ok(updatedUtilisateur) : ResponseEntity.notFound().build();
    }

    @CrossOrigin(origins = "*")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUtilisateur(@PathVariable Long id) {
        utilisateurService.deleteUtilisateur(id);
        return ResponseEntity.noContent().build();
    }
    @CrossOrigin(origins = "*")
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> credentials) {
        String email = credentials.get("email");
        String motdepasse = credentials.get("motdepasse");

        UtilisateurDTO utilisateur = utilisateurService.authenticate(email, motdepasse);
        if (utilisateur != null) {
            return ResponseEntity.ok(utilisateur);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid email or password");
        }
    }
    @PutMapping("/{id}/token")
    public ResponseEntity<UtilisateurDTO> updateToken(@PathVariable Long id, @RequestBody Map<String, String> requestBody) {
        String token = requestBody.get("token"); // Extract the token from the JSON object
        Utilisateur updatedUser = utilisateurService.updateToken(id, token);
        UtilisateurDTO userDTO = modelMapper.map(updatedUser, UtilisateurDTO.class);
        return ResponseEntity.ok(userDTO);
    }
}