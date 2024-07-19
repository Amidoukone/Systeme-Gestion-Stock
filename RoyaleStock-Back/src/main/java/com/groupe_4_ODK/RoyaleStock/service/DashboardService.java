//package com.groupe_4_ODK.RoyaleStock.service;
//
//import com.groupe_4_ODK.RoyaleStock.entite.BonEntrees;
//import com.groupe_4_ODK.RoyaleStock.entite.BonSorties;
//import com.groupe_4_ODK.RoyaleStock.entite.Produits;
//import com.groupe_4_ODK.RoyaleStock.repository.BonEntreRepository;
//import com.groupe_4_ODK.RoyaleStock.repository.BonSortieRepository;
//import com.groupe_4_ODK.RoyaleStock.repository.ProduitsRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.text.SimpleDateFormat;
//import java.util.Date;
//import java.util.List;
//import java.util.Map;
//import java.util.stream.Collectors;
//
//@Service
//public class DashboardService {
//  @Autowired
//  private ProduitsRepository produitRepository;
//
//  @Autowired
//  private BonEntreRepository bonEntreeRepository;
//
//  @Autowired
//  private BonSortieRepository bonSortieRepository;
//  @Autowired
//  private ProduitsRepository produitsRepository;
//
// //List des produits en danger de stock
//  public List<Produits> getLowStockProducts() {
//    return produitRepository.findByQuantiteLessThan(10); //  pour les produits avec un stock < 10
//  }
////Top 10 Bon d'Entre
//  public List<BonEntrees> getRecentBonEntrees() {
//    return bonEntreeRepository.findTop10ByOrderByDateCommandeDesc();
//  }
////Top 10 Bon de sortie
//  public List<BonSorties> getRecentBonSorties() {
//    return bonSortieRepository.findTop10BySortieByDateSortieDesc();
//  }
//  //Top 10 Produits Vendu
//  public List<Produits> getProduitsTopVendu() {
//    return produitsRepository.findTop10ByOrderByQuantiteVenduDesc();
//  }
//  //Filtrage des sorties par moi
//  public Map<String, Double> getsortieByMois() {
//    List<BonSorties> bonSorties = bonSortieRepository.findAll();
//
//    Map<String, Double> sortieByMois = bonSorties.stream()
//      .collect(Collectors.groupingBy(
//        bonSortie -> getMonthYear(bonSortie.getDateSortie()),
//        Collectors.summingDouble(bonSortie -> bonSortie.getDetailsSorties().stream()
//          .mapToDouble(detailsSortie -> detailsSortie.getPrix_unitaire() * detailsSortie.getQuantite())
//          .sum())
//      ));
//
//    return sortieByMois;
//  }
//
//  private String getMonthYear(Date date) {
//    SimpleDateFormat sdf = new SimpleDateFormat("MMM yyyy");
//    return sdf.format(date);
//  }
//}
