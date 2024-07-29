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
  bonEntree: BonEntree = {} as BonEntree;
  produits: Produit[] = [];
  fournisseurs: Fournisseur[] = [];
  selectedFournisseurId: number | any;
  detailsEntrees: DetailEntree[] = [];
  isEditMode: boolean = false;

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

    console.log(this.bonEntree.detailEntrees, this.bonEntree, this.selectedFournisseurId);

    const formattedBonEntree = {
      ...this.bonEntree,
      date_commande: this.bonEntree.dateCommande
        ? format(new Date(this.bonEntree.dateCommande), 'yyyy-MM-dd')
        : null // Handle invalid or missing date
    };

    if (this.isEditMode) {
      this.bonEntreeService.updateBonEntree(this.bonEntree.id, this.bonEntree).subscribe(() => {
        this.router.navigate(['/bon-entree']);
      });
    } else {
      this.bonEntreeService.createBonEntree(this.bonEntree).subscribe(() => {
        this.router.navigate(['/bon-entree']);
      });
    }
  }
}
