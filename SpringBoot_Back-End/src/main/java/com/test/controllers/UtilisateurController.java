package com.test.controllers;

import com.test.entities.Utilisateur;
import com.test.services.UtilisateurService;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/utilisateurs")
public class UtilisateurController {
    @Autowired
    private UtilisateurService utilisateurService;

    @GetMapping
    public ResponseEntity<List<Utilisateur>> getAllUtilisateurs() {
        List<Utilisateur> utilisateurs = utilisateurService.findAll();
        return ResponseEntity.ok(utilisateurs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Utilisateur> getUtilisateurById(@PathVariable int id) {
        Optional<Utilisateur> utilisateur = utilisateurService.findById(id);
        return utilisateur.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/admin")
    public ResponseEntity<Utilisateur> createAdmin(@RequestBody Utilisateur utilisateurRequest) {
        Utilisateur utilisateur = utilisateurService.createAdmin(utilisateurRequest.getUsername(), utilisateurRequest.getEmail(), utilisateurRequest.getPassword());
        return ResponseEntity.ok(utilisateur);
    }

    @PostMapping("/manager")
    public ResponseEntity<Utilisateur> createManager(@RequestBody Utilisateur utilisateurRequest) {
        Utilisateur utilisateur = utilisateurService.createManager(utilisateurRequest.getUsername(), utilisateurRequest.getEmail(), utilisateurRequest.getPassword(), utilisateurRequest.getEntrepot());
        return ResponseEntity.ok(utilisateur);
    }

    /*@PostMapping("/vendeur")
    public ResponseEntity<Utilisateur> createVendeur(@RequestBody Utilisateur utilisateurRequest, @RequestHeader("Authenticated-User") String authenticatedUser, @RequestHeader("Entrepot-Id") int entrepotId) {
        Utilisateur manager = utilisateurService.findById(Integer.parseInt(authenticatedUser)).orElseThrow(() -> new RuntimeException("Manager non trouvé"));
        Entrepot entrepot = utilisateurService.findEntrepotById(entrepotId).orElseThrow(() -> new RuntimeException("Entrepôt non trouvé"));
        Utilisateur utilisateur = utilisateurService.createVendeur(utilisateurRequest.getUsername(), utilisateurRequest.getEmail(), utilisateurRequest.getPassword(), manager);
        return ResponseEntity.ok(utilisateur);
    }*/

    @GetMapping("/entrepots/{entrepotId}")
    public ResponseEntity<List<Utilisateur>> getUtilisateursByEntrepot(@PathVariable int entrepotId) {
        List<Utilisateur> utilisateurs = utilisateurService.findByEntrepot(entrepotId);
        return ResponseEntity.ok(utilisateurs);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Utilisateur> updateUtilisateur(@PathVariable int id, @RequestBody Utilisateur utilisateurDetails) {
        utilisateurDetails.setId(id);
        Utilisateur updatedUtilisateur = utilisateurService.update(utilisateurDetails);
        return updatedUtilisateur != null ? ResponseEntity.ok(updatedUtilisateur) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUtilisateur(@PathVariable int id) {
        utilisateurService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/allUtilisateurs")
    public ResponseEntity<List<Utilisateur>> getAllUtilisateur() {
        List<Utilisateur> utilisateurs = utilisateurService.findAll();
        return ResponseEntity.ok(utilisateurs);
    }


 /*   @Autowired
    private UtilisateurService utilisateurService;

    private static final Logger logger = LoggerFactory.getLogger(UtilisateurController.class);

    @GetMapping
    public List<Utilisateur> getAllUtilisateurs() {
        //logger.info("GET /api/utilisateurs");
        return utilisateurService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Utilisateur> getUtilisateurById(@PathVariable int id) {
        return utilisateurService.findById(id)
                .map(utilisateur -> ResponseEntity.ok().body(utilisateur))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Utilisateur createUtilisateur(@RequestBody Utilisateur utilisateur) {
        return utilisateurService.save(utilisateur);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Utilisateur> updateUtilisateur(@PathVariable int id, @RequestBody Utilisateur utilisateurDetails) {
        return utilisateurService.findById(id)
                .map(utilisateur -> {
                    utilisateur.setUsername(utilisateurDetails.getUsername());
                    utilisateur.setContact(utilisateurDetails.getContact());
                    utilisateur.setEmail(utilisateurDetails.getEmail());
                    utilisateur.setPassword(utilisateurDetails.getPassword());
                    utilisateur.setEntrepot(utilisateurDetails.getEntrepot());
                    utilisateur.setRole(utilisateurDetails.getRole());
                    Utilisateur updatedUtilisateur = utilisateurService.save(utilisateur);
                    return ResponseEntity.ok().body(updatedUtilisateur);
                }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUtilisateur(@PathVariable int id) {
        utilisateurService.deleteById(id);
        return ResponseEntity.noContent().build();
    }*/
}
