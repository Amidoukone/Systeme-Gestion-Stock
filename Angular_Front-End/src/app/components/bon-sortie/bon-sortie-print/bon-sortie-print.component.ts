import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { BonSortieService } from '../../../services/bon-sortie.service';
import { BonSortie } from '../../../models/bon-sortie';
import { DetailSortieService } from '../../../services/detail-sortie.service';
import { DetailSortie } from '../../../models/detail-sortie';
import { Produit } from '../../../models/produit';
import { ProduitService } from '../../../services/produit.service';
import {CommonModule} from "@angular/common";
import {FormsModule} from "@angular/forms";
import { NgxPrintModule } from 'ngx-print';

@Component({
  selector: 'app-bon-sortie-print',
  standalone: true,
  imports: [CommonModule, FormsModule, NgxPrintModule],
  templateUrl: './bon-sortie-print.component.html',
  styleUrl: './bon-sortie-print.component.css'
})
export class BonSortiePrintComponent implements OnInit {
  /*bonSortie: BonSortie = {
    id: 0,
    motif: {
      id: 0,
      title: '',
      createBy:0
    },
    dateSortie: Date,
    utilisateur: { id: 0, username: '' },
    utilisateur_id: 0,
    detailsSorties: DetailSortie,
    produit: Produit,
    produit_id: 0
  };*/
  bonSortie: BonSortie = {} as BonSortie;
  produits: Produit[] = [];
  total: any;

  constructor(
    private route: ActivatedRoute,
    private bonSortieService: BonSortieService,
    private detailSortieService: DetailSortieService,
    private produitService: ProduitService
  ) {}

  ngOnInit(): void {
    const id = this.route.snapshot.paramMap.get('id');
    if (id) {
      this.loadBonSortieById(+id);
    }
    let total = 0;
  }

  loadBonSortieById(id: number): void {
    this.bonSortieService.getBonSortieById(id).subscribe(data => {
      console.log('Bon de Sortie:', data);  // Log pour vérifier les données
      this.bonSortie = data;
    });
  }

  loadProduits(): void {
    this.produitService.getProduits().subscribe(data => {
      this.produits = data;
      this.mapDetailsToProduits();
    });
  }

  mapDetailsToProduits(): void {
    if (this.bonSortie.detailsSorties) {
      this.bonSortie.detailsSorties.forEach(detail => {
        const produit = this.produits.find(p => p.id === detail.produit.id);
        /*if (produit) {
          detail.produit = produit;
        }*/
      });
    }
  }

  getProduitName(produit_id: number): string {
    const produit = this.produits.find(p => p.id === produit_id);
    return produit ? produit.productName : 'N/A';
  }

  getTotalTTC(): number {
    return this.bonSortie.detailsSorties.reduce((total: number, detail: any) => {
      return total + detail.quantity * detail.prix;
    }, 0);
  }

  printDocument(): void {
    const printContents = document.getElementById('print-container')?.innerHTML;
    const originalContents = document.body.innerHTML;
    if (printContents) {
      document.body.innerHTML = printContents;
      window.print();
      document.body.innerHTML = originalContents;
    }
  }
}
