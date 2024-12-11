package smart.gestion.des.equipments.admin.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import smart.gestion.des.equipments.admin.dtos.ProblemeDTO;
import smart.gestion.des.equipments.admin.services.ProblemeService;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/problemes")
public class ProblemeController {

    @Autowired
    private ProblemeService problemeService;

    @PostMapping
    public ResponseEntity<ProblemeDTO> createProbleme(@RequestBody ProblemeDTO problemeDTO) {
        ProblemeDTO createdProbleme = problemeService.createProbleme(problemeDTO);
        return ResponseEntity.ok(createdProbleme);
    }

    @GetMapping
    public ResponseEntity<List<ProblemeDTO>> getAllProblemes() {
        List<ProblemeDTO> problemes = problemeService.getAllProblemes();
        return ResponseEntity.ok(problemes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProblemeDTO> getProblemeById(@PathVariable Long id) {
        ProblemeDTO probleme = problemeService.getProblemeById(id);
        return probleme != null ? ResponseEntity.ok(probleme) : ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProblemeDTO> updateProbleme(@PathVariable Long id, @RequestBody ProblemeDTO problemeDTO) {
        ProblemeDTO updatedProbleme = problemeService.updateProbleme(id, problemeDTO);
        return updatedProbleme != null ? ResponseEntity.ok(updatedProbleme) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProbleme(@PathVariable Long id) {
        problemeService.deleteProbleme(id);
        return ResponseEntity.noContent().build();
    }
}
