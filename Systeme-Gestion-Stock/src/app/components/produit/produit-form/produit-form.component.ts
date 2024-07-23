import { Component, OnInit, NO_ERRORS_SCHEMA } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, Router } from '@angular/router';
import { ProduitService } from '../../../services/produit.service';
import { CategorieService } from '../../../services/categorie.service';
import { Produit } from '../../../models/produit';
import { Categorie } from '../../../models/categorie';
import {FormsModule} from "@angular/forms";

@Component({
  selector: 'app-produit-form',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './produit-form.component.html',
  styleUrl: './produit-form.component.css'
})
export class ProduitFormComponent implements OnInit {

  categories: Categorie[] = [];
  produit: Produit = {} as Produit;
  isEditMode: boolean = false;
  selectedCategoryId: number | null = null;

  constructor(
    private produitService: ProduitService,
    private categorieService: CategorieService,
    private route: ActivatedRoute,
    private router: Router
  ) { }

  ngOnInit(): void {
    this.loadCategories();

    const id = this.route.snapshot.paramMap.get('id');
    if (id) {
      this.isEditMode = true;
      this.loadProduitById(+id);
    }
  }

  async loadCategories(): Promise<void> {
    try {
      this.categories = await this.categorieService.getCategories().toPromise() || [];
      console.log('Categories loaded:', this.categories);
    } catch (error) {
      console.error('Error loading categories:', error);
    }
  }

  async loadProduitById(id: number): Promise<void> {
    try {
      const produit = await this.produitService.getProduitById(id).toPromise();
      if (produit) {
        this.produit = produit;
        this.selectedCategoryId = produit.categorie.id; // Assuming the category is an object with an id
        console.log('Produit loaded:', this.produit);
      }
    } catch (error) {
      console.error('Error loading produit:', error);
    }
  }

  onCategoryChange(event: any): void {
    this.selectedCategoryId = +event.target.value; // Use event.target.value for form elements and convert to number
  }

  async onSubmit(event: Event): Promise<void> {
    event.preventDefault();

    if (this.selectedCategoryId !== null) {
      this.produit.categorie = { id: this.selectedCategoryId } as Categorie; // Create a category object with the selected ID

      try {
        if (this.isEditMode) {
          await this.produitService.updateProduit(this.produit.id, this.produit).toPromise();
        } else {
          await this.produitService.saveProduit(this.produit).toPromise();
        }
        this.router.navigate(['/produits']);
      } catch (error) {
        console.error('Error saving produit:', error);
      }
    } else {
      console.error('Category must be selected');
    }
  }
}
