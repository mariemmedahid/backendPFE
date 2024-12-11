package smart.gestion.des.equipments.admin.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import smart.gestion.des.equipments.admin.dtos.CategorieDTO;
import smart.gestion.des.equipments.admin.dtos.EquipmentDTO;
import smart.gestion.des.equipments.admin.services.CategorieService;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategorieController {

    @Autowired
    private CategorieService categorieService;

    @CrossOrigin(origins = "*")
    @PostMapping
    public ResponseEntity<CategorieDTO> createCategorie(@RequestBody CategorieDTO categorieDTO) {
        CategorieDTO createdCategorie = categorieService.createCategorie(categorieDTO);
        return ResponseEntity.ok(createdCategorie);
    }

    @CrossOrigin(origins = "*")
    @GetMapping
    public ResponseEntity<List<CategorieDTO>> getAllCategories() {
        List<CategorieDTO> categories = categorieService.getAllCategories();
        return ResponseEntity.ok(categories);
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/{id}")
    public ResponseEntity<CategorieDTO> getCategorieById(@PathVariable Long id) {
        CategorieDTO categorie = categorieService.getCategorieById(id);
        return categorie != null ? ResponseEntity.ok(categorie) : ResponseEntity.notFound().build();
    }

    @CrossOrigin(origins = "*")
    @PutMapping("/{id}")
    public ResponseEntity<CategorieDTO> updateCategorie(@PathVariable Long id, @RequestBody CategorieDTO categorieDTO) {
        CategorieDTO updatedCategorie = categorieService.updateCategorie(id, categorieDTO);
        return updatedCategorie != null ? ResponseEntity.ok(updatedCategorie) : ResponseEntity.notFound().build();
    }

    @CrossOrigin(origins = "*")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategorie(@PathVariable Long id) {
        categorieService.deleteCategorie(id);
        return ResponseEntity.noContent().build();
    }
    @CrossOrigin(origins = "*")
    @GetMapping("/{id}/equipments")
    public ResponseEntity<List<EquipmentDTO>> getEquipmentsByCategorie(@PathVariable Long id) {
        List<EquipmentDTO> equipments = categorieService.getEquipmentsByCategorie(id);
        return ResponseEntity.ok(equipments);
    }
}
