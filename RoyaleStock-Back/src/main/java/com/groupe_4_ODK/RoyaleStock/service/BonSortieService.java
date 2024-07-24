package com.groupe_4_ODK.RoyaleStock.service;

import com.groupe_4_ODK.RoyaleStock.entite.*;
import com.groupe_4_ODK.RoyaleStock.repository.*;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;
import java.util.List;

@Service
public class BonSortieService {

  @Autowired
  private BonSortieRepository bonSortieRepository;

  @Autowired
  private EntrepotsRepository entrepotsRepository;

  @Autowired
  private DetailsSortieRepository detailsSortieRepository;

  @Autowired
  private ProduitsRepository produitRepository;

  @Autowired
  private AdminRepository adminRepository;

  @Autowired
  private MethodeUtil methodeUtil;

  @Autowired
  private ManagerRepository managerRepository;
  @Autowired
  private MotifRepository motifRepository;

  @Autowired
  private NotificationService notificationService;
  // Méthode pour récupérer tous les BonSortie
  public List<BonSorties> getAllBonSorties() {
    return bonSortieRepository.findAll();
  }

  // Méthode pour récupérer un BonSortie par son ID
  public Optional<BonSorties> getBonSortieById(Integer id) {
    return bonSortieRepository.findById(id);
  }

  // Méthode pour créer un nouveau BonSortie
  @Transactional
  public BonSorties createBonSortie(BonSorties bonSortie) {
    // Si la date de sortie n'est pas définie, la définir à la date actuelle
    if (bonSortie.getDateSortie() == null) {
      bonSortie.setDateSortie(new Date());
    }

    // Récupérer l'ID de l'utilisateur connecté et l'assigner au bon de sortie
    bonSortie.setUtilisateur(methodeUtil.getCurrentUserId());
    bonSortie.setEntrepot(methodeUtil.getEntrepotByUserId(bonSortie.getUtilisateur()));
    // Sauvegarder le bon de sortie sans mettre à jour les quantités
    bonSortie = bonSortieRepository.save(bonSortie);

    // Initialiser le prix total à zéro
    double totalPrix = 0.0;

    // Vérifier et traiter les détails de sortie
    if (bonSortie.getDetailsSorties() != null) {
      System.out.println("La liste des détails de sortie n'est pas nulle");
      for (DetailsSorties detailsSortie : bonSortie.getDetailsSorties()) {
        System.out.println("Traitement du détail de sortie");

        // Vérifier si le produit est valide
        if (detailsSortie.getProduits() != null && detailsSortie.getProduits().getId() != null) {
          Produits produit = produitRepository.findById(detailsSortie.getProduits().getId())
            .orElseThrow(() -> new RuntimeException("Pas de produit avec cet ID: " + detailsSortie.getProduits().getId()));

          // Calculer la nouvelle quantité du produit après la sortie
          int quantiteSortie = detailsSortie.getQuantite();
          int nouvelleQuantite = produit.getQuantite() - quantiteSortie;

          // Vérifier si la nouvelle quantité est valide
          if (nouvelleQuantite < 0) {
            throw new IllegalArgumentException("Quantité insuffisante pour le produit: " + produit.getNom());
          }

          // Envoyer une notification si la quantité est insuffisante
          if (nouvelleQuantite <= 5) {
            String message = "La quantité du produit " + produit.getNom() + " est maintenant " + nouvelleQuantite + ". Pensez à faire une nouvelle commande pour ce produit.";
            notificationService.sendNotification(methodeUtil.getCurrentUserEmail(), message);
            System.out.println("Email envoyé");
          }

          // Mettre à jour la quantité du produit et enregistrer les modifications
          produit.setQuantite(nouvelleQuantite);
          produitRepository.save(produit);

          // Associer le produit mis à jour au détail de sortie
          detailsSortie.setProduits(produit);
        } else {
          throw new RuntimeException("Produit est null ou n'a pas cet ID");
        }

        // Calculer le prix total
        totalPrix += detailsSortie.getPrix_unitaire() * detailsSortie.getQuantite();
        detailsSortie.setBonSorties(bonSortie);
        detailsSortieRepository.save(detailsSortie);
      }

      // Mettre à jour le prix total du bon de sortie
      bonSortie.setPrixTotal(totalPrix);

      // Sauvegarder le bon de sortie mis à jour avec le prix total calculé
      bonSortie = bonSortieRepository.save(bonSortie);

      return bonSortie;
    } else {
      throw new RuntimeException("La liste des détails de sortie est nulle");
    }
  }

  //Methode pour recuper les bonSorties dune entrepot donner
  public List<BonSorties> getDetailsSortiesByEntrepot(Long entrepotId) {
    // Récupérer l'entrepôt par ID
    Entrepots entrepot = entrepotsRepository.findById(entrepotId)
      .orElseThrow(() -> new RuntimeException("Entrepôt non trouvé avec l'ID: " + entrepotId));

    // Récupérer
    return bonSortieRepository.findByEntrepot(entrepot);
  }
  // Méthode pour mettre à jour un BonSortie existant
  @Transactional
  public BonSorties updateBonSortie(Integer id, BonSorties bonSortie) {
    Optional<BonSorties> existingBonSortieOptional = bonSortieRepository.findById(id);
    if (existingBonSortieOptional.isEmpty()) {
      return null;
    }

    BonSorties existingBonSortie = existingBonSortieOptional.get();
    existingBonSortie.setMotif(bonSortie.getMotif());
    existingBonSortie.setDateSortie(bonSortie.getDateSortie());
    existingBonSortie.setDetailsSorties(bonSortie.getDetailsSorties());

    // Sauvegarder et retourner le BonSortie mis à jour
    return bonSortieRepository.save(existingBonSortie);
  }

  // Méthode pour supprimer un BonSortie par son ID
  public void deleteBonSortie(Integer id) {
    bonSortieRepository.deleteById(id);
  }

  // Méthode pour récupérer les DetailsSortie d'un BonSortie par son ID
  public List<DetailsSorties> getDetailsSortiesByBonSortieId(Long bonSortieId) {
    Optional<BonSorties> bonSortieOptional = bonSortieRepository.findById(Math.toIntExact(bonSortieId));
    return bonSortieOptional.map(BonSorties::getDetailsSorties).orElse(null);
  }

  // Méthode pour récupérer un DetailsSortie par son ID
  public Optional<DetailsSorties> getDetailsSortieById(Integer id) {
    return detailsSortieRepository.findById(id);
  }
  //Produit la plus sortie en fonction du motif
  public Map<String, Map<String, Integer>> getTopProductsByMotif() {
    List<BonSorties> bonSorties = bonSortieRepository.findAll();
    Map<String, Map<String, Integer>> topProductsByMotif = new HashMap<>();

    for (BonSorties bonSortie : bonSorties) {
      Motif motif = bonSortie.getMotif();
      if (motif == null) {
        continue; // ignore les bons de sortie sans motif
      }
      String motifTitle = motif.getTitle();
      Map<String, Integer> productCountMap = topProductsByMotif.getOrDefault(motifTitle, new HashMap<>());

      for (DetailsSorties detailsSortie : bonSortie.getDetailsSorties()) {
        Produits produit = detailsSortie.getProduits();
        if (produit != null) {
          String productName = produit.getNom();
          productCountMap.put(productName, Integer.valueOf(productCountMap.getOrDefault(productName, 0) + detailsSortie.getQuantite()));
        }
      }

      topProductsByMotif.put(motifTitle, productCountMap);
    }

    return topProductsByMotif;
  }
  //Imprimer BonSortie
  public void imprimerBonSortie(Integer id) {
    BonSorties bonSortie = getBonSortieById(id).orElse(null);

    Document document = new Document();
    try {
      PdfWriter.getInstance(document, new FileOutputStream("BonSortie_" + id + ".pdf"));
      document.open();

      // Ajout un en-tête au document
      document.add(new Paragraph("Bon de Sortie", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16, BaseColor.BLACK)));

      // Ajout les détails en haut du document
      assert bonSortie != null;
      document.add(new Paragraph("ID: " + bonSortie.getId()));
      document.add(new Paragraph("Date de Sortie: " + bonSortie.getDateSortie()));
      document.add(new Paragraph("\n"));

      // Création du tableau pour les détails de sortie
      PdfPTable table = new PdfPTable(4); // 4 colonnes
      table.setWidthPercentage(100);

      // Ajout des en-têtes de colonnes
      PdfPCell cell = new PdfPCell(new Paragraph("Produit", FontFactory.getFont(FontFactory.HELVETICA_BOLD)));
      cell.setHorizontalAlignment(Element.ALIGN_CENTER);
      table.addCell(cell);

      cell = new PdfPCell(new Paragraph("Quantité", FontFactory.getFont(FontFactory.HELVETICA_BOLD)));
      cell.setHorizontalAlignment(Element.ALIGN_CENTER);
      table.addCell(cell);

      cell = new PdfPCell(new Paragraph("Prix unitaire", FontFactory.getFont(FontFactory.HELVETICA_BOLD)));
      cell.setHorizontalAlignment(Element.ALIGN_CENTER);
      table.addCell(cell);

      cell = new PdfPCell(new Paragraph("Prix total", FontFactory.getFont(FontFactory.HELVETICA_BOLD)));
      cell.setHorizontalAlignment(Element.ALIGN_CENTER);
      table.addCell(cell);

      // Ajout des lignes de données
      double totalGeneral = 0;
      for (DetailsSorties detailsSortie : bonSortie.getDetailsSorties()) {
        double prixTotal = detailsSortie.getProduits().getPrixVente() * detailsSortie.getQuantite();
        totalGeneral += prixTotal;

        table.addCell(detailsSortie.getProduits().getNom());
        table.addCell(String.valueOf(detailsSortie.getQuantite()));
        table.addCell(String.valueOf(detailsSortie.getProduits().getPrixVente()));
        totalGeneral =detailsSortie.getPrix_unitaire()*detailsSortie.getQuantite();
        table.addCell(String.valueOf(totalGeneral));
      }

      // Ajouter une ligne vide
      cell = new PdfPCell(new Paragraph("Total"));
      cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
      cell.setColspan(3);
      table.addCell(cell);

      // Ajouter le total général
      cell = new PdfPCell(new Paragraph(String.valueOf(totalGeneral), FontFactory.getFont(FontFactory.HELVETICA_BOLD)));
      cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
      table.addCell(cell);


      // Ajouter le tableau au document
      document.add(table);


      document.add(new Paragraph("\n\nManager: " + bonSortie));

      document.close();
    } catch (DocumentException | IOException e) {
      e.printStackTrace();
    }
  }

}
