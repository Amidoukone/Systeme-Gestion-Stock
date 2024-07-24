package com.groupe_4_ODK.RoyaleStock.controller;

import com.groupe_4_ODK.RoyaleStock.entite.BonSorties;
import com.groupe_4_ODK.RoyaleStock.entite.DetailsSorties;
import com.groupe_4_ODK.RoyaleStock.service.BonSortieService;
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
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("api/bonSortie")
public class BonSortieController {
  @Autowired
  BonSortieService bonSortieService;
  //les Endpoint pour les sorties
  // pour récupérer tous les BonSorties
  @GetMapping("/list")
  public ResponseEntity<List<BonSorties>> getAllBonSorties() {
    List<BonSorties> bonSorties = bonSortieService.getAllBonSorties();
    return ResponseEntity.ok(bonSorties);
  }

  //pour récupérer un BonSortie par son ID
  @GetMapping("/{id}")
  public ResponseEntity<BonSorties> getBonSortieById(@PathVariable Integer id) {
    Optional<BonSorties> bonSortie = bonSortieService.getBonSortieById(id);
    return bonSortie.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
  }
  //Creer une sortie
  @PostMapping("/create")
  public ResponseEntity<BonSorties> createBonSortie(@RequestBody BonSorties bonSortie) {
    try {
      BonSorties createdBonSortie = bonSortieService.createBonSortie(bonSortie);
      return new ResponseEntity<>(createdBonSortie, HttpStatus.CREATED);
    } catch (IllegalArgumentException e) {
      return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    } catch (Exception e) {
      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<String> handleExceptions(Exception e) {
    return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
  }
  @GetMapping("/topProduitByMotif")
  public ResponseEntity<Map<String, Map<String, Integer>>> getTopProductsByMotif() {
    Map<String, Map<String, Integer>> topProductsByMotif = bonSortieService.getTopProductsByMotif();
    return ResponseEntity.ok(topProductsByMotif);
  }
  //Bon Sortie d'une entrepot
  @GetMapping("/list/{entrepotId}")
  public List<BonSorties> getDetailsSortiesByEntrepot(@PathVariable Long entrepotId) {
    return bonSortieService.getDetailsSortiesByEntrepot(entrepotId);
  }
//Imprimer une sortie
@GetMapping("/imprimer/{id}")
public ResponseEntity<byte[]> imprimerBonSortie(@PathVariable Integer id) {
  bonSortieService.imprimerBonSortie(id);

  File pdfFile = new File("BonSortie_" + id + ".pdf");
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

  // pour mettre à jour un BonSortie existant par son ID
  @PutMapping("/sortie/update/{id}")
  public ResponseEntity<BonSorties> updateBonSortie(@PathVariable Integer id, @RequestBody BonSorties bonSortie) {
    BonSorties updatedBonSortie = bonSortieService.updateBonSortie(id, bonSortie);
    if (updatedBonSortie != null) {
      return ResponseEntity.ok(updatedBonSortie);
    } else {
      return ResponseEntity.notFound().build();
    }
  }

  //pour supprimer un BonSortie par son ID
  @DeleteMapping("/delete/{id}")
  public ResponseEntity<Void> deleteBonSortie(@PathVariable Integer id) {
    bonSortieService.deleteBonSortie(id);
    return ResponseEntity.noContent().build();
  }

  //pour récupérer les DetailsSortie d'un BonSortie par son ID
  @GetMapping("/{id}/detailssorties")
  public ResponseEntity<List<DetailsSorties>> getDetailsSortiesByBonSortieId(@PathVariable Long id) {
    List<DetailsSorties> detailsSorties = bonSortieService.getDetailsSortiesByBonSortieId(id);
    return ResponseEntity.ok(detailsSorties);
  }

  // pour récupérer un DetailsSortie par son ID
//  @GetMapping("detailssorties/{id}")
//    public ResponseEntity<DetailsSorties> getDetailsSortieById(@PathVariable Long id) {
//        Optional<DetailsSorties> detailsSortie = detailsSortiService.getDetailsSortieById(id);
//        return detailsSortie.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
//    }
}
