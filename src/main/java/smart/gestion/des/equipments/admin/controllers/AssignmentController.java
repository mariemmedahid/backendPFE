package smart.gestion.des.equipments.admin.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import smart.gestion.des.equipments.admin.dtos.AssignmentDTO;
import smart.gestion.des.equipments.admin.dtos.EquipmentDTO;
import smart.gestion.des.equipments.admin.services.AssignmentService;
import smart.gestion.des.equipments.dao.entities.Equipment;

import java.util.List;
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/assignments")
public class AssignmentController {

    @Autowired
    private AssignmentService assignmentService;

    @PostMapping
    public ResponseEntity<AssignmentDTO> createAssignment(@RequestBody AssignmentDTO assignmentDTO) {
        AssignmentDTO createdAssignment = assignmentService.createAssignment(assignmentDTO);
        return ResponseEntity.ok(createdAssignment);
    }

    @GetMapping
    public ResponseEntity<java.lang.Object> getAllAssignments() {
        List<AssignmentDTO> assignments = assignmentService.getAllAssignments();
        return ResponseEntity.ok(assignments);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AssignmentDTO> getAssignmentById(@PathVariable Long id) {
        AssignmentDTO assignment = assignmentService.getAssignmentById(id);
        return assignment != null ? ResponseEntity.ok(assignment) : ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<AssignmentDTO> updateAssignment(@PathVariable Long id, @RequestBody AssignmentDTO assignmentDTO) {
        AssignmentDTO updatedAssignment = assignmentService.updateAssignment(id, assignmentDTO);
        return updatedAssignment != null ? ResponseEntity.ok(updatedAssignment) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAssignment(@PathVariable Long id) {
        assignmentService.deleteAssignment(id);
        return ResponseEntity.noContent().build();
    }
    @GetMapping("/user/{userId}")
    public List<EquipmentDTO> getAllEquipmentByUserId(@PathVariable Long userId) {
        return assignmentService.getAllEquipmentByUserId(userId);
    }

    @DeleteMapping("/remove-assignment")
    public ResponseEntity<?> removeAssignment(@RequestParam Long userId, @RequestParam Long equipmentId) {
        assignmentService.removeAssignment(userId, equipmentId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/user/{userId}/equipments")
    public ResponseEntity<List<EquipmentDTO>> getEquipmentsByUserId(@PathVariable Long userId) {
        List<EquipmentDTO> equipmentList = assignmentService.getEquipmentsByUserId(userId);
        return ResponseEntity.ok(equipmentList);
    }

}
