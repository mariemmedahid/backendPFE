package smart.gestion.des.equipments.admin.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import smart.gestion.des.equipments.admin.dtos.CategorieDTO;
import smart.gestion.des.equipments.admin.dtos.EquipmentDTO;
import smart.gestion.des.equipments.admin.services.EquipmentService;
import smart.gestion.des.equipments.dao.entities.Equipment;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/equipments")
public class EquipmentController {

    @Autowired
    private EquipmentService equipmentService;

    @PostMapping
    public ResponseEntity<EquipmentDTO> createEquipment(@RequestBody EquipmentDTO equipmentDTO) {
        EquipmentDTO createdEquipment = equipmentService.createEquipment(equipmentDTO);
        return ResponseEntity.ok(createdEquipment);
    }

    @GetMapping
    public ResponseEntity<List<EquipmentDTO>> getAllEquipments() {
        List<EquipmentDTO> equipments = equipmentService.getAllEquipments();
        return ResponseEntity.ok(equipments);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EquipmentDTO> getEquipmentById(@PathVariable Long id) {
        EquipmentDTO equipment = equipmentService.getEquipmentById(id);
        return equipment != null ? ResponseEntity.ok(equipment) : ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<EquipmentDTO> updateEquipment(@PathVariable Long id, @RequestBody EquipmentDTO equipmentDTO) {
        EquipmentDTO updatedEquipment = equipmentService.updateEquipment(id, equipmentDTO);
        return updatedEquipment != null ? ResponseEntity.ok(updatedEquipment) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEquipment(@PathVariable Long id) {
        equipmentService.deleteEquipment(id);
        return ResponseEntity.noContent().build();
    }

}
