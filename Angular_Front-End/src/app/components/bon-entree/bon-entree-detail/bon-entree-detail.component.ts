import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router, RouterLink, RouterModule } from '@angular/router';
import { BonEntreeService } from '../../../services/bon-entree.service';
import { ProduitService } from '../../../services/produit.service';
import { DetailEntreeService } from '../../../services/detail-entree.service';
import { DetailEntree } from '../../../models/detail-entree';
import { Produit } from '../../../models/produit';
import { BonEntree } from '../../../models/bon-entree';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-bon-entree-detail',
  standalone: true,
  imports: [CommonModule, RouterLink, RouterModule, FormsModule],
  templateUrl: './bon-entree-detail.component.html',
  styleUrls: ['./bon-entree-detail.component.css']
})
export class BonEntreeDetailComponent implements OnInit {
  detailEntree: DetailEntree = { produit: {} as Produit } as DetailEntree;
  bonEntree: BonEntree = {} as BonEntree;
  produits: Produit[] = [];
  bonEntreeId: number;
  successMessage: string = '';
  errorMessage: string = '';

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private bonEntreeService: BonEntreeService,
    private produitService: ProduitService,
    private detailEntreeService: DetailEntreeService
  ) {
    this.bonEntreeId = +this.route.snapshot.paramMap.get('id')!;
  }

  ngOnInit(): void {
    this.loadProduits();
    this.loadBonEntree();
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

  loadBonEntree(): void {
    this.bonEntreeService.getBonEntreeById(this.bonEntreeId).subscribe(
      data => {
        this.bonEntree = data;
        if (!this.bonEntree.detailEntrees) {
          this.bonEntree.detailEntrees = [];
        }
      },
      error => {
        console.error('Error loading bon entree:', error);
        this.errorMessage = 'Erreur lors du chargement du bon d\'entrée.';
        setTimeout(() => this.errorMessage = '', 3000);
      }
    );
  }

  onSubmit(): void {
    this.detailEntree.bonEntree = this.bonEntree;
    this.detailEntreeService.createDetailEntree(this.detailEntree).subscribe(
      () => {
        this.successMessage = 'Détail ajouté avec succès!';
        setTimeout(() => this.successMessage = '', 3000);
        this.router.navigate(['/bon-entree']);
      },
      error => {
        console.error('Error saving detail entree:', error);
        this.errorMessage = 'Erreur lors de l\'ajout du détail.';
        setTimeout(() => this.errorMessage = '', 3000);
      }
    );
  }
}
