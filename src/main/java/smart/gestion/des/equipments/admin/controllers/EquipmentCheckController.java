package smart.gestion.des.equipments.admin.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import smart.gestion.des.equipments.admin.dtos.EquipmentCheckDTO;
import smart.gestion.des.equipments.admin.services.EquipmentCheckService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/equipment-checks")
public class EquipmentCheckController {

    @Autowired
    private EquipmentCheckService equipmentCheckService;

    // Create
    @PostMapping
    public ResponseEntity<EquipmentCheckDTO> createEquipmentCheck(@RequestBody EquipmentCheckDTO equipmentCheckDTO) {
        EquipmentCheckDTO createdEquipmentCheck = equipmentCheckService.createEquipmentCheck(equipmentCheckDTO);
        return ResponseEntity.ok(createdEquipmentCheck);
    }

    // Get all
    @GetMapping
    public ResponseEntity<List<EquipmentCheckDTO>> getAllEquipmentChecks() {
        List<EquipmentCheckDTO> equipmentChecks = equipmentCheckService.getAllEquipmentChecks();
        return ResponseEntity.ok(equipmentChecks);
    }

    // Get by ID
    @GetMapping("/{id}")
    public ResponseEntity<EquipmentCheckDTO> getEquipmentCheckById(@PathVariable Long id) {
        Optional<EquipmentCheckDTO> equipmentCheck = equipmentCheckService.getEquipmentCheckById(id);
        return equipmentCheck.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Update
    @PutMapping("/{id}")
    public ResponseEntity<EquipmentCheckDTO> updateEquipmentCheck(@PathVariable Long id, @RequestBody EquipmentCheckDTO equipmentCheckDTO) {
        EquipmentCheckDTO updatedEquipmentCheck = equipmentCheckService.updateEquipmentCheck(id, equipmentCheckDTO);
        return ResponseEntity.ok(updatedEquipmentCheck);
    }

    // Delete
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEquipmentCheck(@PathVariable Long id) {
        equipmentCheckService.deleteEquipmentCheck(id);
        return ResponseEntity.noContent().build();
    }
}
