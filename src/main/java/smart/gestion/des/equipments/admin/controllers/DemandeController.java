package smart.gestion.des.equipments.admin.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import smart.gestion.des.equipments.admin.dtos.DemandeDTO;
import smart.gestion.des.equipments.admin.services.DemandeService;
import smart.gestion.des.equipments.dao.entities.Demande;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/demandes")
public class DemandeController {

    @Autowired
    private DemandeService demandeService;

    @PostMapping
    public ResponseEntity<Demande> createDemande(@RequestBody DemandeDTO demandeDTO) {
        Demande createdDemande = demandeService.createDemande(demandeDTO);
        return ResponseEntity.ok(createdDemande);
    }

    @GetMapping
    public ResponseEntity<List<Demande>> getAllDemandes() {
        List<Demande> demandes = demandeService.getAllDemandes();
        return ResponseEntity.ok(demandes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DemandeDTO> getDemandeById(@PathVariable Long id) {
        Optional<Demande> demande = demandeService.getDemandeById(id);
        return demande != null ? (ResponseEntity<DemandeDTO>) ResponseEntity.ok() : ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<DemandeDTO> updateDemande(@PathVariable Long id, @RequestBody DemandeDTO demandeDTO) {
        Demande updatedDemande = demandeService.updateDemande(id, demandeDTO);
        return updatedDemande != null ? (ResponseEntity<DemandeDTO>) ResponseEntity.ok() : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDemande(@PathVariable Long id) {
        demandeService.deleteDemande(id);
        return ResponseEntity.noContent().build();
    }
}
