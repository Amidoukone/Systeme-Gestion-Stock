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

    private final UtilisateurService utilisateurService;

    @Autowired
    public UtilisateurController(UtilisateurService utilisateurService) {
        this.utilisateurService = utilisateurService;
    }

    @PostMapping("/admin")
    public ResponseEntity<Utilisateur> createAdmin(@RequestBody Utilisateur utilisateur) {
        try {
            Utilisateur newAdmin = utilisateurService.createAdmin(utilisateur.getUsername(), utilisateur.getContact(), utilisateur.getEmail(), utilisateur.getPassword());
            return ResponseEntity.ok(newAdmin);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PostMapping("/manager")
    public ResponseEntity<Utilisateur> createManager(@RequestBody Utilisateur utilisateur) {
        try {
            Utilisateur newManager = utilisateurService.createManager(utilisateur.getUsername(), utilisateur.getContact(), utilisateur.getEmail(), utilisateur.getPassword());
            return ResponseEntity.ok(newManager);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }
    /*@PostMapping("/manager")
    public ResponseEntity<Utilisateur> createManager(@RequestBody Utilisateur utilisateurRequest) {
        Utilisateur utilisateur = utilisateurService.createManager(utilisateurRequest.getUsername(), utilisateurRequest.getContact(), utilisateurRequest.getEmail(), utilisateurRequest.getPassword(), utilisateurRequest.getEntrepot());
        return ResponseEntity.ok(utilisateur);
    }*/

    @PostMapping("/vendeur")
    public ResponseEntity<Utilisateur> createVendeur(@RequestBody Utilisateur utilisateur) {
        try {
            Utilisateur newVendeur = utilisateurService.createVendeur(utilisateur.getUsername(), utilisateur.getContact(), utilisateur.getEmail(), utilisateur.getPassword());
            return ResponseEntity.ok(newVendeur);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping("/entrepots/{entrepotId}")
    public ResponseEntity<List<Utilisateur>> getUtilisateursByEntrepot(@PathVariable int entrepotId) {
        List<Utilisateur> utilisateurs = utilisateurService.findByEntrepot(entrepotId);
        return ResponseEntity.ok(utilisateurs);
    }

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

 /*
 @Autowired
    private UtilisateurService utilisateurService;



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

    @PostMapping("/vendeur")
    public ResponseEntity<Utilisateur> createVendeur(@RequestBody Utilisateur utilisateur) {
        try {
            Utilisateur newVendeur = utilisateurService.createVendeur(utilisateur.getUsername(), utilisateur.getEmail(), utilisateur.getPassword());
            return ResponseEntity.ok(newVendeur);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }



 */
}
