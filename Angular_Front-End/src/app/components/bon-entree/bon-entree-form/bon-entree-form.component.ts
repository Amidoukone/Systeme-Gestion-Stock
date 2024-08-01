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
  successMessage: string = '';
  errorMessage: string = '';

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
      // Assurez-vous que detailsEntrees est initialisé
      this.detailsEntrees = [];
    }
  }

  loadProduits(): void {
    this.produitService.getProduits().subscribe(data => {
      this.produits = data;
    });
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
    this.bonEntree.detailEntrees = this.detailsEntrees;
    this.bonEntree.fournisseur = this.selectedFournisseurId
      ? this.fournisseurs?.find(f => f.id === +this.selectedFournisseurId) ?? {} as Fournisseur
      : {} as Fournisseur;

    const formattedBonEntree = {
      ...this.bonEntree,
      date_commande: this.bonEntree.dateCommande
        ? format(new Date(this.bonEntree.dateCommande), 'yyyy-MM-dd')
        : null // Handle invalid or missing date
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
      this.bonEntreeService.createBonEntree(formattedBonEntree).subscribe(() => {
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
