import {Component, OnInit} from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {CommonModule} from "@angular/common";
import {FormsModule} from "@angular/forms";
import {BonEntree} from "../../../models/bon-entree";
import {BonEntreeService} from "../../../services/bon-entree.service";
import { DetailEntree } from '../../../models/detail-entree';
import {DetailEntreeService} from "../../../services/detail-entree.service";
import {Produit} from "../../../models/produit";
import {ProduitService} from "../../../services/produit.service";

@Component({
  selector: 'app-bon-entree-print',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './bon-entree-print.component.html',
  styleUrl: './bon-entree-print.component.css'
})
export class BonEntreePrintComponent implements OnInit {
  /*bonEntree: BonEntree = {
    id: 0,
    dateCommande: new Date(),
    statut: '',
    utilisateur: { id: 0, username: '' },
    utilisateur_id: 0,
    fournisseur: { id: 0, fournName: ''},
    fournisseur_id: 0,
    detailEntrees: [],
    produit_id: 0,
    produit: Produit
  };*/
  bonEntree: BonEntree = {} as BonEntree;
  //produit: Produit[] = [];
  //total: any;

  constructor(
    private route: ActivatedRoute,
    private bonEntreeService: BonEntreeService,
    private detailEntreeService: DetailEntreeService,
    private produitService: ProduitService
  ) {}

  ngOnInit(): void {
    const id = this.route.snapshot.paramMap.get('id');
    if (id) {
      this.loadBonEntreeById(+id);
    }
    //let total = 0;
  }

  loadBonEntreeById(id: number): void {
    this.bonEntreeService.getBonEntreeById(id).subscribe(data => {
      this.bonEntree = data;
      /*console.log(data);
      this.loadProduits();*/
    });
  }

  /*loadProduits(): void {
    this.produitService.getProduits().subscribe(data => {
      this.produit = data;
      this.mapDetailsToProduits();
    });
  }*/

  /* mapDetailsToProduits(): void {
    if (this.bonEntree.detailEntrees) {
      this.bonEntree.detailEntrees.forEach(detail => {
        const produit = this.produit.find(p => p.id === detail.produit_id);
        if (produit) {
          detail.produit = produit;
        }
      });
    }
  }

  getProduitName(produit_id: number): string {
    const produit = this.produit.find(p => p.id === produit_id);
    return produit ? produit.productName : 'N/A';
  }

  getTotalTTC(): number {
    return this.bonEntree.detailEntrees.reduce((total: number, detail: any) => {
      return total + detail.quantite * detail.produit.prix;
    }, 0);
  }*/

  getTotalTTC(): number {
    return this.bonEntree.detailEntrees?.reduce((total, detail) => total + (detail.quantite * detail.prix), 0) || 0;
  }

  printDocument(): void {
    const printContents = document.getElementById('print-container')?.innerHTML;
    const originalContents = document.body.innerHTML;
    const printimp = document.getElementById('imp');

    if (printContents) {
      if (printimp) {
        printimp.style.display = 'none';
      }

      document.body.innerHTML = printContents;
      window.print();
      document.body.innerHTML = originalContents;
      if (printimp) {
        printimp.style.display = 'block';
      }
    }
  }
}
