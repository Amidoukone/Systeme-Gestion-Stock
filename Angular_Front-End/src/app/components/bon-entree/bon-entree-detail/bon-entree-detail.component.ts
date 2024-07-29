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
import { Fournisseur } from '../../../models/fournisseur';
import { Utilisateur } from '../../../models/utilisateur';

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
    this.produitService.getProduits().subscribe(data => {
      this.produits = data;
    });
  }

  loadBonEntree(): void {
    this.bonEntreeService.getBonEntreeById(this.bonEntreeId).subscribe(data => {
      this.bonEntree = data;
      if (!this.bonEntree.detailEntrees) {
        this.bonEntree.detailEntrees = [];
      }
    });
  }

  onSubmit(): void {
    this.detailEntree.bonEntree = this.bonEntree;
    this.detailEntreeService.createDetailEntree(this.detailEntree).subscribe(() => {
      this.router.navigate(['/bon-entree']);
    });
  }
}
