import { Component, OnInit, NO_ERRORS_SCHEMA } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, Router } from '@angular/router';
import { BonSortieService } from '../../../services/bon-sortie.service';
import { BonSortie } from '../../../models/bon-sortie';
import { FormsModule } from '@angular/forms';
import { DetailSortie } from '../../../models/detail-sortie';
import { Produit } from '../../../models/produit';
import { ProduitService } from '../../../services/produit.service';
import {AuthService} from "../../../services/auth.service";
import { format } from 'date-fns';
import {BonEntree} from "../../../models/bon-entree";

@Component({
  selector: 'app-bon-sortie-form',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './bon-sortie-form.component.html',
  styleUrl: './bon-sortie-form.component.css'
})
export class BonSortieFormComponent implements OnInit {
  //bonSortie: BonSortie;
  bonSortie: BonSortie = {
    id: 0,
    motif: { id: 0, title: '', createBy:0 },
    dateSortie: new Date(),
    utilisateur: { id: 0, username: '' },
    utilisateur_id: 0,
    detailsSorties: [],
  };
  produits: Produit[] = [];
  detailsSorties: DetailSortie[] = [];
  isEditMode: boolean = false;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private bonSortieService: BonSortieService,
    private authService: AuthService,
    private produitService: ProduitService
  ) { }

  ngOnInit(): void {
    this.loadProduits();
    const id = this.route.snapshot.paramMap.get('id');
    if (id) {
      this.isEditMode = true;
      this.loadBonSortieById(+id);
    } else {
      this.bonSortie.utilisateur.id = this.authService.currentUserValue?.id || 0;
    }
  }

  async loadProduits() {
    try {
      this.produits = await this.produitService.getProduits().toPromise() || [];
    } catch (error) {
      console.error('Error loading products:', error);
    }
  }

  async loadBonSortieById(id: number) {
    try {
      const bonSortie = await this.bonSortieService.getBonSortieById(id).toPromise();
      if (bonSortie) {
        this.bonSortie = bonSortie;
        this.detailsSorties = bonSortie.detailsSorties || [];
      }
    } catch (error) {
      console.error('Error loading BonSortie:', error);
    }
  }

  addDetailSortie(): void {
    this.detailsSorties.push({
      id: 0,
      produit_id: 0,
      quantity: 0,
      prix: 0,
      bon_sortie_id: this.bonSortie.id,
      produit: {} as Produit
    } as DetailSortie);
  }

  onSubmit(): void {
    // Assuming this.bonSortie.detailsSorties contains DetailSortie objects with produit_id set
    this.bonSortie.detailsSorties.forEach(detail => {
      const produit = this.produits.find(p => p.id === detail.produit_id);
     /*if (produit) {
        detail.produit = produit;
      }*/
    });

    if (this.isEditMode) {
      console.log(this.bonSortie);
      this.bonSortieService.updateBonSortie(this.bonSortie.id, this.bonSortie).subscribe(() => {
        this.router.navigate(['/bon-sortie']);
      }, error => {
        console.error('Error updating bon sortie:', error);
      });
    } else {
      this.bonSortieService.createBonSortie(this.bonSortie).subscribe(() => {
        this.router.navigate(['/bon-sortie']);
      }, error => {
        console.error('Error creating bon sortie:', error);
      });
    }
  }
}
