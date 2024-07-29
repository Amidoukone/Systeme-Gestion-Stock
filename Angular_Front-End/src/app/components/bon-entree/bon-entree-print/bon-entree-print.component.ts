import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from "@angular/router";
import { CommonModule } from "@angular/common";
import { FormsModule } from "@angular/forms";
import { BonEntree } from "../../../models/bon-entree";
import { BonEntreeService } from "../../../services/bon-entree.service";
import { DetailEntreeService } from "../../../services/detail-entree.service";
import { ProduitService } from "../../../services/produit.service";

@Component({
  selector: 'app-bon-entree-print',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './bon-entree-print.component.html',
  styleUrls: ['./bon-entree-print.component.css']
})
export class BonEntreePrintComponent implements OnInit {
  bonEntree: BonEntree = {} as BonEntree;

  constructor(
    private route: ActivatedRoute,
    private bonEntreeService: BonEntreeService,
  ) {}

  ngOnInit(): void {
    const id = this.route.snapshot.paramMap.get('id');
    if (id) {
      this.loadBonEntreeById(+id);
    }
  }

  loadBonEntreeById(id: number): void {
    this.bonEntreeService.getBonEntreeById(id).subscribe(
      data => {
        console.log('BonEntree data:', data); // Log the data to verify
        this.bonEntree = data;
      },
      error => console.error('Error fetching BonEntree', error)
    );
  }

  getTotalTTC(): number {
    if (!this.bonEntree || !this.bonEntree.detailEntrees) {
      return 0;
    }
    return this.bonEntree.detailEntrees.reduce((total, detail) => total + (detail.quantite * detail.prix), 0);
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
