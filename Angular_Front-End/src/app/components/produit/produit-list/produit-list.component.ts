import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { Router } from "@angular/router";
import { NgbModal, NgbModalRef } from "@ng-bootstrap/ng-bootstrap";
import { NgxPaginationModule } from "ngx-pagination";
import { Produit } from '../../../models/produit';
import { AuthService } from '../../../services/auth.service';
import { ProduitService } from '../../../services/produit.service';

@Component({
  selector: 'app-produit-list',
  standalone: true,
  imports: [CommonModule, NgxPaginationModule],
  templateUrl: './produit-list.component.html',
  styleUrl: './produit-list.component.css'
})
export class ProduitListComponent implements OnInit {
  produits: Produit[] = [];
  filteredProduits: Produit[] = [];

  page: number = 1;
  itemsPerPage: number = 6;  // Nombre d'éléments par page
 
  produitsToDelete: number | null = null;
  produitsToEdit: number | null = null;
  private modalRef: NgbModalRef | null = null;
  errorMessage = '';
  infoMessage = '';

  constructor(private produitService: ProduitService, private authService: AuthService, private router: Router, private modalService: NgbModal) { }

  ngOnInit(): void {
    this.loadProduits();

  }

  loadProduits(): void {
    const currentUser = this.authService.currentUserValue;
    if (currentUser && currentUser.entrepot) {
      const entrepotId = currentUser.entrepot.entrepotId;
      this.produitService.getProduitsByEntrepot(entrepotId).subscribe(produits => {
        if (produits.length === 0) {
          this.infoMessage = 'Aucun produit trouvée pour cet Entrepot.';
          setTimeout(() => this.infoMessage = '', 2000);
        }else{

          this.produits = produits;
          console.log(this.produits);

        }
      }, error => {
        console.error('Erreur lors de la récupération des produits:', error);
        this.errorMessage = 'Erreur lors de la récupération des produits.';
      });
    } else {
      this.errorMessage = 'Erreur: entrepôt utilisateur non trouvé';
    }
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

  printQRCode(): void {
    window.print();
  }

  applyFilter(event: Event): void {
    const filterValue = (event.target as HTMLInputElement).value.trim().toLowerCase();
    this.filteredProduits = this.produits.filter(produit =>
      produit.productName.toLowerCase().includes(filterValue)
    );
  }

  showDeleteConfirmation(content: any, id: number): void {
    this.produitsToDelete = id;
    this.modalRef = this.modalService.open(content);
  }

  confirmDelete(): void {
    if (this.produitsToDelete!== null) {
      this.produitService.deleteProduit(this.produitsToDelete).subscribe(() => {
        this.produits = this.produits.filter(f => f.id !== this.produitsToDelete);
        this.filteredProduits = this.filteredProduits.filter(f => f.id !== this.produitsToDelete);
        this.produitsToDelete = null;
        this.modalRef?.close();
      });
    }
  }


    protected readonly Produit = Produit;
}
