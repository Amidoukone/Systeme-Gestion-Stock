package com.groupe_4_ODK.RoyaleStock.service;

import com.groupe_4_ODK.RoyaleStock.entite.BonSorties;
import com.groupe_4_ODK.RoyaleStock.entite.DetailsSorties;
import com.groupe_4_ODK.RoyaleStock.entite.Motif;
import com.groupe_4_ODK.RoyaleStock.entite.Produits;
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
  private DetailsSortieRepository detailsSortieRepository;

  @Autowired
  private ProduitsRepository produitRepository;

  @Autowired
  private AdminRepository adminRepository;

  @Autowired
  private ManagerRepository managerRepository;
  @Autowired
  private MotifRepository motifRepository;

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
        try {
            if (bonSortie.getDateSortie() == null) {
                bonSortie.setDateSortie(new Date());
            }
            // Vérification et mise à jour des entités associées
//            if (bonSortie.getManager() != null && bonSortie.getManager().getId() != null) {
//                bonSortie.setManager(managerRepository.findById(bonSortie.getManager().getId()).orElse(null));
//            }
//            if (bonSortie.getAdmin() != null && bonSortie.getAdmin().getId() != null) {
//                bonSortie.setAdmin(adminRepository.findById(bonSortie.getAdmin().getId()).orElse(null));
//            }
            for (DetailsSorties detailsSortie : bonSortie.getDetailsSorties()) {
                Produits produit = detailsSortie.getProduits();
                if (produit == null || produit.getId() == null) {
                    throw new IllegalArgumentException("Produit invalide pour le détail de sortie");
                }

                // Récupérer le produit depuis la base de données
                Produits finalProduit = produitRepository.findById(produit.getId())
                        .orElseThrow(() -> new IllegalArgumentException("Produit non trouvé pour l'ID: " + produit.getId()));

                // Calculer la nouvelle quantité
                int quantiteSortie = Integer.parseInt(String.valueOf(detailsSortie.getQuantite()));
                int nouvelleQuantite = finalProduit.getQuantite() - quantiteSortie;

                if (nouvelleQuantite < 0) {
                    throw new IllegalArgumentException("Quantité insuffisante pour le produit: " + finalProduit.getNom());
                }

                // Mettre à jour la quantité du produit
                finalProduit.setQuantite(nouvelleQuantite);
                produitRepository.save(finalProduit);
            }
            bonSortie = bonSortieRepository.save(bonSortie);

            for (DetailsSorties detailsSortie : bonSortie.getDetailsSorties()) {
                detailsSortie.setBonSorties(bonSortie);
                detailsSortieRepository.save(detailsSortie);
            }

            return bonSortie;
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la création du bon de sortie", e);
        }
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
    existingBonSortie.setManager(bonSortie.getManager());
    existingBonSortie.setAdmin(bonSortie.getAdmin());

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
        table.addCell(String.valueOf(prixTotal));
      }

      // Ajouter une ligne vide
      cell = new PdfPCell(new Paragraph(""));
      cell.setColspan(3);
      table.addCell(cell);

      // Ajouter le total général
      cell = new PdfPCell(new Paragraph("Total", FontFactory.getFont(FontFactory.HELVETICA_BOLD)));
      cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
      table.addCell(cell);

      table.addCell(new PdfPCell(new Paragraph(String.valueOf(bonSortie.getTotal()), FontFactory.getFont(FontFactory.HELVETICA_BOLD))));

      // Ajouter le tableau au document
      document.add(table);


      document.add(new Paragraph("\n\nManager: " + bonSortie.getManager().getNom()));

      document.close();
    } catch (DocumentException | IOException e) {
      e.printStackTrace();
    }
  }

}
