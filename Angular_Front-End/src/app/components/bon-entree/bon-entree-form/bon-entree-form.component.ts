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
import {AuthService} from "../../../services/auth.service";
import {FormsModule} from "@angular/forms";
import { format } from 'date-fns';

@Component({
  selector: 'app-bon-entree-form',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './bon-entree-form.component.html',
  styleUrl: './bon-entree-form.component.css'
})
export class BonEntreeFormComponent implements OnInit {
  bonEntree: {
    id: number;
    dateCommande: Date;
    statut: string;
    utilisateur: { id: number; username: string };
    fournisseur: { id: number; fournName: string };
    detailEntrees: any[]
  } = {} as BonEntree;
  fournisseurs: Fournisseur[] = [];
  utilisateurs: Utilisateur[] = [];
  produits: Produit[] = [];
  detailsEntrees: DetailEntree[] = [];
  isEditMode: boolean = false;
  selectedFournisseurId: number | null = null;

  constructor(
    private bonEntreeService: BonEntreeService,
    private fournisseurService: FournisseurService,
    private utilisateurService: UtilisateurService,
    private produitService: ProduitService,
    private authService: AuthService,
    private route: ActivatedRoute,
    private router: Router
  ) { }

  ngOnInit(): void {
    this.bonEntree = {
      id: 0,
      dateCommande: new Date(),
      statut: '',
      utilisateur: { id: 0, username: '' },
      fournisseur: { id: 0, fournName: '' },
      detailEntrees: []
    };

    this.loadFournisseursUtilisateursProduits();
    const id = this.route.snapshot.paramMap.get('id');
    if (id) {
      this.isEditMode = true;
      this.loadBonEntreeById(+id);
    } else {
      this.selectedFournisseurId = this.selectedFournisseurId;
      this.bonEntree.utilisateur.id = this.authService.currentUserValue?.id;
      this.detailsEntrees = [];
    }
  }

  async loadFournisseursUtilisateursProduits() {
    try {
      this.fournisseurs = await this.fournisseurService.getFournisseurs().toPromise() || [];
      this.utilisateurs = this.authService.currentUserValue.id;
      this.produits = await this.produitService.getProduits().toPromise() || [];
    } catch (error) {
      console.error('Error loading ', error);
    }
  }

  async loadBonEntreeById(id: number) {
    try {
      const bonEntree = await this.bonEntreeService.getBonEntreeById(id).toPromise();
      if (bonEntree) {
        this.bonEntree = bonEntree;
        this.selectedFournisseurId = bonEntree.fournisseur_id;
        this.detailsEntrees = bonEntree.detailEntrees || [];
      }
    } catch (error) {
      console.error('Error loading bonEntree:', error);
    }
  }

  onFournisseurChange(event: any): void {
    this.selectedFournisseurId = event.value;
  }

  addDetailEntree(): void {
    this.detailsEntrees.push({
      produit_id: 0,
      quantite: 0,
      prix: 0,
      bon_entree_id: this.bonEntree.id
    } as DetailEntree);
  }

  onSubmit(): void {
    this.bonEntree.detailEntrees = this.detailsEntrees;

    const formattedBonEntree = {
      ...this.bonEntree,
      date_commande: format(this.bonEntree.dateCommande, 'yyyy-MM-dd')
    };

    if (this.isEditMode) {
      this.bonEntreeService.updateBonEntree(this.bonEntree.id, formattedBonEntree).subscribe( () => {
        this.router.navigate(['/bon-entree']);
      },error => {
        console.error('Error updating bonEntree:', error);
      });
    } else {
        this.bonEntreeService.createBonEntree(formattedBonEntree).subscribe(() => {
          this.router.navigate(['/bon-entree']);
      }, error => {
        console.error('Error creating bonEntree:', error);
      });
    }
  }
}
