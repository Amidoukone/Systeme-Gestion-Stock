import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormsModule } from "@angular/forms";
import { ActivatedRoute, Router } from '@angular/router';
import { format } from 'date-fns';
import { BonEntree } from '../../../models/bon-entree';
import { DetailEntree } from '../../../models/detail-entree';
import { Fournisseur } from '../../../models/fournisseur';
import { Produit } from '../../../models/produit';
import { Utilisateur } from '../../../models/utilisateur';
import { AuthService } from "../../../services/auth.service";
import { BonEntreeService } from '../../../services/bon-entree.service';
import { FournisseurService } from '../../../services/fournisseur.service';
import { ProduitService } from '../../../services/produit.service';

@Component({
  selector: 'app-bon-entree-form',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './bon-entree-form.component.html',
  styleUrls: ['./bon-entree-form.component.css']
})
export class BonEntreeFormComponent implements OnInit {
  bonEntree: BonEntree = {} as BonEntree;
  produits: Produit[] = [];
  fournisseurs: Fournisseur[] = [];
  selectedFournisseurId: number | any;
  detailsEntrees: DetailEntree[] = [];
  isEditMode: boolean = false;
  successMessage= '';
  errorMessage= '';
  infoMessage= '';

  constructor(
    private bonEntreeService: BonEntreeService,
    private fournisseurService: FournisseurService,
    private produitService: ProduitService,
    private authService: AuthService,
    private route: ActivatedRoute,
    private router: Router
  ) { }

  ngOnInit(): void {
    this.bonEntree.utilisateur = { id: this.authService.currentUserValue?.id, username: this.authService.currentUserValue?.username } as Utilisateur;
    this.loadProduits();
    this.loadFournisseurs();
    const id = this.route.snapshot.paramMap.get('id');
    if (id) {
      this.isEditMode = true;
      this.loadBonEntreeById(+id);
    } else {
      this.detailsEntrees = [];
    }
  }

  loadProduits(): void {
    const currentUser = this.authService.currentUserValue;
    if (currentUser && currentUser.entrepot) {
      const entrepotId = currentUser.entrepot.entrepotId;
      this.produitService.getProduitsByEntrepot(entrepotId).subscribe(produits => {
        if (produits.length === 0) {
          this.infoMessage = 'Aucun produit trouvée pour cet Entrepot.';
          setTimeout(() => this.infoMessage = '', 2000);
        }else{
          this.produits = produits;
        }
      }, error => {
        console.error('Erreur lors de la récupération des produits:', error);
        this.errorMessage = 'Erreur lors de la récupération des produits.';
      });
    } else {
      this.errorMessage = 'Erreur: entrepôt utilisateur non trouvé';
    }
  }

  loadFournisseurs(): void {
    this.fournisseurService.getFournisseurs().subscribe(data => {
      this.fournisseurs = data;
    });
  }

  loadBonEntreeById(id: number): void {
    this.bonEntreeService.getBonEntreeById(id).subscribe(data => {
      this.bonEntree = data;
      this.detailsEntrees = data.detailEntrees || [];
      this.selectedFournisseurId = data.fournisseur.id;
    }, error => {
      console.error('Error loading Bon d\'Entrée:', error);
      this.errorMessage = 'Erreur lors du chargement du bon d\'entrée.';
      setTimeout(() => this.errorMessage = '', 3000);
    });
  }

  onFournisseurChange(event: any): void {
    this.selectedFournisseurId = event.target.value;
  }

  onSubmit(): void {
    const currentUserEmail = this.authService.currentUserValue?.email; 

    if (!currentUserEmail) {
      this.errorMessage = 'Erreur : utilisateur non authentifié.';
      setTimeout(() => this.errorMessage = '', 3000);
      return;
    }

    this.bonEntree.detailEntrees = this.detailsEntrees;
    this.bonEntree.fournisseur = this.selectedFournisseurId
      ? this.fournisseurs?.find(f => f.id === +this.selectedFournisseurId) ?? {} as Fournisseur
      : {} as Fournisseur;

    const formattedBonEntree = {
      ...this.bonEntree,
      date_commande: this.bonEntree.dateCommande
        ? format(new Date(this.bonEntree.dateCommande), 'yyyy-MM-dd')
        : null 
    };

    if (this.isEditMode) {
      this.bonEntreeService.updateBonEntree(this.bonEntree.id, formattedBonEntree).subscribe(() => {
        this.successMessage = 'Bon d\'Entrée mis à jour avec succès!';
        setTimeout(() => this.successMessage = '', 3000);
        setTimeout(() => this.router.navigate(['/bon-entree']), 3000);
      }, error => {
        console.error('Error updating Bon d\'Entrée:', error);
        this.errorMessage = 'Erreur lors de la mise à jour du bon d\'entrée.';
        setTimeout(() => this.errorMessage = '', 3000);
      });
    } else {
      this.bonEntreeService.createBonEntree(formattedBonEntree, currentUserEmail).subscribe(() => {
        this.successMessage = 'Bon d\'Entrée créé avec succès!';
        setTimeout(() => this.successMessage = '', 3000);
        setTimeout(() => this.router.navigate(['/bon-entree']), 3000);
      }, error => {
        console.error('Error creating Bon d\'Entrée:', error);
        this.errorMessage = 'Erreur lors de la création du bon d\'entrée.';
        setTimeout(() => this.errorMessage = '', 3000);
      });
    }
  }

  navigateToBonEntree() {
    this.router.navigate(['/bon-entree']);
  }
}
