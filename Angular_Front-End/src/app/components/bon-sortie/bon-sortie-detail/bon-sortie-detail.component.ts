import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router, RouterLink, RouterModule } from '@angular/router';
import { BonSortieService } from '../../../services/bon-sortie.service';
import { ProduitService } from '../../../services/produit.service';
import { DetailSortieService } from '../../../services/detail-sortie.service';
import { DetailSortie } from '../../../models/detail-sortie';
import { Produit } from '../../../models/produit';
import { BonSortie } from '../../../models/bon-sortie';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-bon-sortie-detail',
  standalone: true,
  imports: [CommonModule, RouterLink, RouterModule, FormsModule],
  templateUrl: './bon-sortie-detail.component.html',
  styleUrls: ['./bon-sortie-detail.component.css']
})
export class BonSortieDetailComponent implements OnInit {
  detailSortie: DetailSortie = { produit: {} as Produit } as DetailSortie;
  bonSortie: BonSortie = {} as BonSortie;
  produits: Produit[] = [];
  bonSortieId: number;
  successMessage: string = '';
  errorMessage: string = '';

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private bonSortieService: BonSortieService,
    private produitService: ProduitService,
    private detailSortieService: DetailSortieService
  ) {
    this.bonSortieId = +this.route.snapshot.paramMap.get('id')!;
  }

  ngOnInit(): void {
    this.loadProduits();
    this.loadBonSortie();
  }

  loadProduits(): void {
    this.produitService.getProduits().subscribe(
      data => this.produits = data,
      error => {
        console.error('Error loading produits:', error);
        this.errorMessage = 'Erreur lors du chargement des produits.';
        setTimeout(() => this.errorMessage = '', 3000);
      }
    );
  }

  loadBonSortie(): void {
    this.bonSortieService.getBonSortieById(this.bonSortieId).subscribe(
      data => {
        this.bonSortie = data;
        if (!this.bonSortie.detailsSorties) {
          this.bonSortie.detailsSorties = [];
        }
      },
      error => {
        console.error('Error loading bon sortie:', error);
        this.errorMessage = 'Erreur lors du chargement du bon de sortie.';
        setTimeout(() => this.errorMessage = '', 3000);
      }
    );
  }

  onSubmit(): void {
    this.detailSortie.bonSortie = this.bonSortie;
    this.detailSortieService.createDetailSortie(this.detailSortie).subscribe(
      () => {
        this.successMessage = 'Détail ajouté avec succès!';
        setTimeout(() => this.successMessage = '', 3000);
        this.router.navigate(['/bon-sortie']);
      },
      error => {
        console.error('Error saving detail sortie:', error);
        this.errorMessage = 'Erreur lors de l\'ajout du détail.';
        setTimeout(() => this.errorMessage = '', 3000);
      }
    );
  }
}
