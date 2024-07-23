package com.groupe_4_ODK.RoyaleStock.service;

import com.groupe_4_ODK.RoyaleStock.entite.BonEntrees;
import com.groupe_4_ODK.RoyaleStock.entite.DetailsEntrees;
import com.groupe_4_ODK.RoyaleStock.entite.Produits;
import com.groupe_4_ODK.RoyaleStock.enums.Statut;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;


@Service
public class BonEntreService {
  @Autowired
  private BonEntreRepository bonEntreRepository;

  @Autowired
  private DetailsEntreRepository detailEntreRepository;

  @Autowired
  private ProduitsRepository produitRepository;

  @Autowired
  private MethodeUtil methodeUtil;

  @Autowired
  private DetailsEntreRepository detailsEntreRepository;

  // Méthode pour récupérer tous les BonEntre
  public List<BonEntrees> getAllBonEntrees() {
    return bonEntreRepository.findAll();
  }
  // Méthode pour récupérer un BonEntre par son ID
  public BonEntrees getBonEntreeById(Integer id) {
    return bonEntreRepository.findById(id)
      .orElseThrow(() -> new RuntimeException("BonEntree non trouve avec ID: " + id));
  }
  //Creer un bon Entre
  @Transactional
  public BonEntrees creeBonEntre(BonEntrees bonEntre) {

    if (bonEntre.getDateCommande() == null) {
      bonEntre.setDateCommande(new Date()); // Utilisation de la date actuelle
    }
    bonEntre.setUser(methodeUtil.getCurrentUserId());
    bonEntre.setStatut(Statut.Encours);

    // Sauvegarder le BonEntree sans mettre à jour les quantités
    bonEntre = bonEntreRepository.save(bonEntre);

    double prixTotal = 0.0;

    // Associer les détails du BonEntree
    if (bonEntre.getDetailsEntrees() != null) {
      System.out.println("La liste des détails d'entrées n'est nulle");
      for (DetailsEntrees detailsEntre : bonEntre.getDetailsEntrees()) {
      System.out.println("bon detail entre");
      if (detailsEntre.getProduits() != null && detailsEntre.getProduits().getId() != null) {
        Produits produit = produitRepository.findById(detailsEntre.getProduits().getId())
          .orElseThrow(() -> new RuntimeException("Pas de produit avec ce ID: " + detailsEntre.getProduits().getId()));

        // Mettre à jour le prix d'achat du produit
        produit.setPrixAchats(detailsEntre.getPrixUnitaire());

        detailsEntre.setProduits(produit);
      } else {
        throw new RuntimeException("Produit est null ou n'a pas ce ID");
      }

      // Calculer le prix total
      prixTotal += detailsEntre.getPrixUnitaire() * detailsEntre.getQuantite();
      detailsEntre.setBonEntrees(bonEntre);
      detailsEntreRepository.save(detailsEntre);
    }

    // Mettre à jour le prix total du BonEntree
    bonEntre.setPrixTotal(prixTotal);

    // Sauvegarder le BonEntree mis à jour avec le prix total calculé
    bonEntre = bonEntreRepository.save(bonEntre);

    return bonEntre;
  }else {
      throw new RuntimeException("La liste des détails d'entrées est nulle");
    }
  }

  @Transactional
  public BonEntrees validerBonEntre(Integer bonEntreId) {
    BonEntrees bonEntre = bonEntreRepository.findById(bonEntreId)
      .orElseThrow(() -> new RuntimeException("BonEntree pas trouvé"));

    // Mettre à jour la quantité du produit pour chaque DetailsEntree
    for (DetailsEntrees detailsEntre : bonEntre.getDetailsEntrees()) {
      if (detailsEntre.getProduits() != null && detailsEntre.getProduits().getId() != null) {
        Produits produit = produitRepository.findById(detailsEntre.getProduits().getId())
          .orElseThrow(() -> new RuntimeException("Produit non trouvé avec ce ID: " + detailsEntre.getProduits().getId()));
        // Ajouter la quantité spécifiée à la quantité actuelle du produit
        produit.setQuantite(produit.getQuantite() + detailsEntre.getQuantite());
        produitRepository.save(produit);
      } else {
        throw new RuntimeException("Produit est null ou n'a pas ce ID");
      }
      detailsEntreRepository.save(detailsEntre);
    }

    // Valider le bon d'entrée en mettant à jour son état
    bonEntre.setStatut(Statut.Livre);
    return bonEntreRepository.save(bonEntre);
  }
  // Méthode pour mettre à jour un BonEntre existant
  public BonEntrees updateBonEntree(Integer id, BonEntrees bonEntreeDetails) {
    BonEntrees bonEntree = getBonEntreeById(id);

    bonEntree.setDateCommande(bonEntreeDetails.getDateCommande());
    bonEntree.setPrixTotal(bonEntreeDetails.getPrixTotal());
    bonEntree.setStatut(bonEntreeDetails.getStatut());
    bonEntree.setFournisseurs(bonEntreeDetails.getFournisseurs());
    bonEntree.setDetailsEntrees(bonEntreeDetails.getDetailsEntrees());


    return bonEntreRepository.save(bonEntree);
  }

  //Supprimer Un bon Entre
  public void deleteBonEntree(Integer id) {
    BonEntrees bonEntree = getBonEntreeById(id);
    bonEntreRepository.delete(bonEntree);
  }

  // Méthode pour récupérer les DetailsEntre d'un BonEntre par son ID
  public List<DetailsEntrees> getDetailsEntresByBonEntreId(Long bonEntreId) {
    Optional<BonEntrees> bonEntreOptional = bonEntreRepository.findById(Math.toIntExact(bonEntreId));
    return bonEntreOptional.map(BonEntrees::getDetailsEntrees).orElse(null);
  }

  // Méthode pour récupérer un DetailsEntre par son ID
  public Optional<DetailsEntrees> getDetailsEntreById(Integer id) {
    return detailsEntreRepository.findById(id);
  }
  //Imprimer
  public void imprimerBonEntree(Integer id) {
    BonEntrees bonEntree = getBonEntreeById(id);

    Document document = new Document();
    try {
      PdfWriter.getInstance(document, new FileOutputStream("BonEntree_" + id + ".pdf"));
      document.open();

      // Ajouter un en-tête au document
      document.add(new Paragraph("Bon d'Entree", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16, BaseColor.BLACK)));

      // Ajouter les détails en haut du document
      document.add(new Paragraph("ID: " + bonEntree.getId()));
      document.add(new Paragraph("Date Commande: " + bonEntree.getDateCommande()));
      document.add(new Paragraph("Statut: " + bonEntree.getStatut()));
      document.add(new Paragraph("\n"));

      // Création du tableau pour les détails d'entrée
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
      for (DetailsEntrees detailsEntree : bonEntree.getDetailsEntrees()) {
        table.addCell(detailsEntree.getProduits().getNom());
        table.addCell(String.valueOf(detailsEntree.getQuantite()));
        table.addCell(String.valueOf(detailsEntree.getPrixUnitaire()));
        table.addCell(String.valueOf(detailsEntree.getPrixUnitaire() * detailsEntree.getQuantite()));
      }

      // Ajouter une ligne vide
      cell = new PdfPCell(new Paragraph("Total"));
      cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
      cell.setColspan(3);
      table.addCell(cell);

      // Ajouter le total général
      cell = new PdfPCell(new Paragraph(String.valueOf(bonEntree.getPrixTotal()), FontFactory.getFont(FontFactory.HELVETICA_BOLD)));
      cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
      table.addCell(cell);

//      table.addCell(new PdfPCell(new Paragraph(String.valueOf(bonEntree.getPrixTotal()), FontFactory.getFont(FontFactory.HELVETICA_BOLD))));

      // Ajouter le tableau au document
      document.add(table);

      // Ajouter les détails en bas à gauche
      document.add(new Paragraph("\n\nFournisseur: " + bonEntree.getFournisseurs().getNom()));

      document.close();
    } catch (DocumentException | IOException e) {
      e.printStackTrace();
    }
  }

}
