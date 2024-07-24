package com.groupe_4_ODK.RoyaleStock.controller;

import com.groupe_4_ODK.RoyaleStock.entite.BonEntrees;
import com.groupe_4_ODK.RoyaleStock.entite.BonSorties;
import com.groupe_4_ODK.RoyaleStock.entite.DetailsEntrees;
import com.groupe_4_ODK.RoyaleStock.service.BonEntreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/bonEntre")
public class BonEntreController {
  @Autowired
  private BonEntreService bonEntreService;
  //BonEntre
  //Create BonEntre
  @PostMapping("/create")
  public ResponseEntity<BonEntrees> createBonEntree(@RequestBody BonEntrees bonEntree) {
    BonEntrees createdBonEntree = bonEntreService.creeBonEntre(bonEntree);
    return ResponseEntity.ok(createdBonEntree);
  }

  @PutMapping("/validate/{id}")
  public ResponseEntity<BonEntrees> validateBonEntree(@PathVariable Integer id) {
    BonEntrees validatedBonEntree = bonEntreService.validerBonEntre(id);
    return ResponseEntity.ok(validatedBonEntree);
  }
  //Listes des BonEntre d'une Entrepots
  @GetMapping("/list/{entrepotId}")
  public List<BonEntrees> getBonEntreByEntrepot(@PathVariable Long entrepotId) {
    return bonEntreService.getBonEntreByEntrepot(entrepotId);
  }
  //Liste de tout BonEntre
  @GetMapping("/list")
  public ResponseEntity<List<BonEntrees>> getAllBonEntrees() {
    List<BonEntrees> bonEntreesList = bonEntreService.getAllBonEntrees();
    return ResponseEntity.ok(bonEntreesList);
  }
  //GetBonById
  @GetMapping("/getbonById/{id}")
  public ResponseEntity<BonEntrees> getBonEntreeById(@PathVariable Integer id) {
    BonEntrees bonEntree = bonEntreService.getBonEntreeById(id);
    return ResponseEntity.ok(bonEntree);
  }

  // Endpoint pour mettre à jour un BonEntre existant
  @PutMapping("/update/{id}")
  public ResponseEntity<BonEntrees> updateBonEntree(@PathVariable Integer id, @RequestBody BonEntrees bonEntreeDetails) {
    BonEntrees updatedBonEntree = bonEntreService.updateBonEntree(id, bonEntreeDetails);
    return ResponseEntity.ok(updatedBonEntree);
  }
  //Delete
  @DeleteMapping("/delete/{id}")
  public ResponseEntity<Void> deleteBonEntree(@PathVariable Integer id) {
    bonEntreService.deleteBonEntree(id);
    return ResponseEntity.noContent().build();
  }
  // Endpoint pour récupérer un DetailsEntre par son ID
  @GetMapping("/get/{id}")
  public ResponseEntity<DetailsEntrees> getDetailsEntreById(@PathVariable("id") Integer id) {
    Optional<DetailsEntrees> detailsEntreOptional = bonEntreService.getDetailsEntreById(id);
    return detailsEntreOptional.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
  }
  //Imprimer
  @GetMapping("/imprimer/{id}")
  public ResponseEntity<byte[]> imprimerBonEntree(@PathVariable Integer id) {
    bonEntreService.imprimerBonEntree(id);

    File pdfFile = new File("BonEntree_" + id + ".pdf");
    byte[] contents = null;

    try (InputStream inputStream = new FileInputStream(pdfFile)) {
      contents = inputStream.readAllBytes();
    } catch (IOException e) {
      e.printStackTrace();
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(org.springframework.http.MediaType.APPLICATION_PDF);
    headers.setContentDispositionFormData("attachment", pdfFile.getName());
    headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
    headers.setContentLength(contents.length);

    return new ResponseEntity<>(contents, headers, HttpStatus.OK);
  }
}
