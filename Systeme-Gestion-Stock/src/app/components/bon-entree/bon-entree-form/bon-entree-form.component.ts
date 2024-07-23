import { Component, OnInit, NO_ERRORS_SCHEMA } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, Router } from '@angular/router';
import { BonEntreeService } from '../../../services/bon-entree.service';
import { FournisseurService } from '../../../services/fournisseur.service';
import { UtilisateurService } from '../../../services/utilisateur.service';
import { ProduitService } from '../../../services/produit.service';
import { BonEntree } from '../../../models/bon-entree';
import { Fournisseur } from '../../../models/fournisseur';
import { Utilisateur } from '../../../models/utilisateur';
import { Produit } from '../../../models/produit';
import { DetailEntree } from '../../../models/detail-entree';
import {FormsModule} from "@angular/forms";

@Component({
  selector: 'app-bon-entree-form',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './bon-entree-form.component.html',
  styleUrl: './bon-entree-form.component.css'
})
export class BonEntreeFormComponent implements OnInit {
  bonEntree: BonEntree = {} as BonEntree;
  fournisseurs: Fournisseur[] = [];
  utilisateurs: Utilisateur[] = [];
  produits: Produit[] = [];
  detailsEntrees: DetailEntree[] = [];
  isEditMode: boolean = false;
  selectedFournisseurId: number | null = null;
  selectedUtilisateurId: number | null = null;

  constructor(
    private bonEntreeService: BonEntreeService,
    private fournisseurService: FournisseurService,
    private utilisateurService: UtilisateurService,
    private produitService: ProduitService,
    private route: ActivatedRoute,
    private router: Router
  ) { }

  ngOnInit(): void {
    this.loadFournisseursUtilisateursProduits();

    const id = this.route.snapshot.paramMap.get('id');
    if (id) {
      this.isEditMode = true;
      this.loadBonEntreeById(+id);
    }
  }

  async loadFournisseursUtilisateursProduits() {
    try {
      this.fournisseurs = await this.fournisseurService.getFournisseurs().toPromise() || [];
      this.utilisateurs = await this.utilisateurService.getAllUtilisateurs().toPromise() || [];
      this.produits = await this.produitService.getProduits().toPromise() || [];
      console.log('Fournisseurs loaded:', this.fournisseurs);
      console.log('Utilisateurs loaded:', this.utilisateurs);
      console.log('Produits loaded:', this.produits);
    } catch (error) {
      console.error('Error loading fournisseurs, utilisateurs, and produits:', error);
    }
  }

  async loadBonEntreeById(id: number) {
    try {
      const bonEntree = await this.bonEntreeService.getBonEntreeById(id).toPromise();
      if (bonEntree) {
        this.bonEntree = bonEntree;
        this.selectedFournisseurId = bonEntree.fournisseur_id;
        this.selectedUtilisateurId = bonEntree.utilisateur_id;
        this.detailsEntrees = bonEntree.detailEntrees || [];
        console.log('BonEntree loaded:', this.bonEntree);
      }
    } catch (error) {
      console.error('Error loading bonEntree:', error);
    }
  }

  onFournisseurChange(event: any): void {
    this.selectedFournisseurId = event.value;
  }

  onUtilisateurChange(event: any): void {
    this.selectedUtilisateurId = event.value;
  }

  addDetailEntree(): void {
    this.detailsEntrees.push({
      produit_id: 0,
      quantite: 0,
      prix_total: 0,
      bon_entree_id: this.bonEntree.id
    } as DetailEntree);
  }

  async onSubmit(event: Event): Promise<void> {
    event.preventDefault();

    if (this.selectedFournisseurId !== null && this.selectedUtilisateurId !== null) {
      this.bonEntree.fournisseur_id = this.selectedFournisseurId;
      this.bonEntree.utilisateur_id = this.selectedUtilisateurId;
      this.bonEntree.detailEntrees = this.detailsEntrees;

      try {
        const formattedBonEntree = {
          ...this.bonEntree,
          date_commande: this.bonEntree.dateCommande.toISOString().split('T')[0]  // Ensure date is in correct format
        };

        if (this.isEditMode) {
          await this.bonEntreeService.updateBonEntree(this.bonEntree.id, formattedBonEntree).toPromise();
        } else {
          await this.bonEntreeService.createBonEntree(formattedBonEntree).toPromise();
        }
        this.router.navigate(['/bon-entrees']);
      } catch (error) {
        console.error('Error saving bonEntree:', error);
      }
    } else {
      console.error('Fournisseur, Utilisateur, and Manager must be selected');
    }
  }
}
