package com.groupe_4_ODK.RoyaleStock.controller;

import com.groupe_4_ODK.RoyaleStock.entite.BonEntrees;
import com.groupe_4_ODK.RoyaleStock.entite.BonSorties;
import com.groupe_4_ODK.RoyaleStock.entite.Produits;
import com.groupe_4_ODK.RoyaleStock.repository.ProduitsRepository;
import com.groupe_4_ODK.RoyaleStock.service.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/dashboard")
public class DashboardController {
  @Autowired
  private DashboardService dashboardService;
  @Autowired
  private ProduitsRepository produitsRepository;

  @GetMapping("/low-stock")
  public ResponseEntity<List<Produits>> getLowStockProducts() {
    return ResponseEntity.ok(dashboardService.getLowStockProducts());
  }

  @GetMapping("/dernierEntre")
  public ResponseEntity<List<BonEntrees>> getRecentBonEntrees() {
    return ResponseEntity.ok(dashboardService.getRecentBonEntrees());
  }
//les dernier produits sortie
  @GetMapping("/dernierSortie")
  public ResponseEntity<List<BonSorties>> getRecentBonSorties() {
    return ResponseEntity.ok(dashboardService.getRecentBonSorties());
  }
  //Liste des 
  @GetMapping("/top10Vendu")
  public ResponseEntity<List<Produits>> getProduitsTopVendu() {
    return ResponseEntity.ok(dashboardService.getProduitsTopVendu());
  }
  //Sortie par moi
  @GetMapping("/monthly-sales")
  public ResponseEntity<Map<String, Double>> getMonthlySales() {
    Map<String, Double> monthlySales = dashboardService.getsortieByMois();
    return ResponseEntity.ok(monthlySales);
  }

}
