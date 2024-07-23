import {Component, NO_ERRORS_SCHEMA, OnInit} from '@angular/core';
import { CommonModule } from '@angular/common';
import { ProduitService } from '../../../services/produit.service';
import { Produit } from '../../../models/produit';
import {HttpClient} from "@angular/common/http";
import {Router} from "@angular/router";

@Component({
  selector: 'app-produit-list',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './produit-list.component.html',
  styleUrl: './produit-list.component.css'
})
export class ProduitListComponent implements OnInit {
  produits: Produit[] = [];
  filteredProduits: Produit[] = [];

  constructor(private produitService: ProduitService, private router: Router) { }

  ngOnInit(): void {
    this.produitService.getProduits().subscribe(data => {
      this.produits = data;
      this.filteredProduits = data;
      console.log('Produits loaded:', this.produits);
    });
  }

  addProduit(): void {
    this.router.navigate(['/add-produit']);
  }

  editProduit(id: number): void {
    this.router.navigate(['/edit-produit', id]);
  }

  deleteProduit(id: number): void {
    this.produitService.deleteProduit(id).subscribe(() => {
      this.produits = this.produits.filter(p => p.id !== id);
      this.filteredProduits = this.filteredProduits.filter(p => p.id !== id);
    });
  }

  applyFilter(event: Event): void {
    const filterValue = (event.target as HTMLInputElement).value.trim().toLowerCase();
    this.filteredProduits = this.produits.filter(produit =>
      produit.productName.toLowerCase().includes(filterValue)
    );
  }
}
