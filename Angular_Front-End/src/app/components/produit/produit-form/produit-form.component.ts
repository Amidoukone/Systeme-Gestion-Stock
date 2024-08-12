import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { Categorie } from '../../../models/categorie';
import { Produit } from '../../../models/produit';
import { AuthService } from '../../../services/auth.service';
import { CategorieService } from '../../../services/categorie.service';
import { ProduitService } from '../../../services/produit.service';

@Component({
  selector: 'app-produit-form',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './produit-form.component.html',
  styleUrls: ['./produit-form.component.css']
})
export class ProduitFormComponent implements OnInit {
  categories: Categorie[] = [];
  produit: Produit = {} as Produit;
  isEditMode: boolean = false;
  // qrCodeUrl: string = '';
  selectedCategoryId: number | null = null;
  successMessage: string = '';
  errorMessage: string = '';
  qrCodeUrl: string | undefined;

  constructor(
    private produitService: ProduitService,
    private categorieService: CategorieService,
    private route: ActivatedRoute,
    private router: Router,
    private authService: AuthService
  ) { }

  ngOnInit(): void {
    this.loadCategories();

    const id = this.route.snapshot.paramMap.get('id');
    if (id) {
      this.isEditMode = true;
      this.loadProduitById(+id);
    }
  }

  // async loadCategories() {
  //   try {
  //     this.categories = await this.categorieService.getCategories().toPromise() || [];
  //     console.log('Categories loaded:', this.categories);
  //   } catch (error) {
  //     console.error('Error loading categories:', error);
  //     this.errorMessage = 'Erreur lors du chargement des catégories.';
  //     setTimeout(() => this.errorMessage = '', 3000);
  //   }
  // }
  loadCategories(): void {
    const currentUser = this.authService.currentUserValue;
    if (!currentUser || !currentUser.email) {
      this.errorMessage = 'Erreur: email utilisateur non trouvé';
      return;
    }
    const email = currentUser.email;

    this.categorieService.getCategoriesForCurrentUser(email).subscribe(categories => {
      if (categories.length === 0) {
        this.errorMessage = 'Aucune catégorie trouvée pour cet entrepôt.';
        setTimeout(() => this.errorMessage = '', 2000);
      } else {
        this.categories = categories;
      }
    }, error => {
      console.error('Erreur lors de la récupération des catégories:', error);
      this.errorMessage = 'Erreur lors de la récupération des catégories.';
      setTimeout(() => this.errorMessage = '', 2000);
    });
  }

  async loadProduitById(id: number) {
    try {
      const produit = await this.produitService.getProduitById(id).toPromise();
      if (produit) {
        this.produit = produit;
        this.selectedCategoryId = produit.categorie.id;
        console.log('Produit loaded:', this.produit);
      }
    } catch (error) {
      console.error('Error loading produit:', error);
      this.errorMessage = 'Erreur lors du chargement du produit.';
      setTimeout(() => this.errorMessage = '', 3000);
    }
  }

  onCategoryChange(event: any): void {
    this.selectedCategoryId = +event.target.value;
  }

  // async onSubmit(event: Event): Promise<void> {
  //   event.preventDefault();
  //   this.produit.createBy = this.authService.currentUserValue.id;
  //   if (this.selectedCategoryId !== null) {
  //     this.produit.categorie = {
  //       id: this.selectedCategoryId
  //     } as Categorie;

  //     try {
  //       if (this.isEditMode) {
  //         await this.produitService.updateProduit(this.produit.id, this.produit).toPromise();
  //         this.successMessage = 'Produit mis à jour avec succès!';
  //       } else {
  //         await this.produitService.createProduit(this.produit).toPromise();
  //         this.successMessage = 'Produit ajouté avec succès!';
  //       }
  //       setTimeout(() => this.successMessage = '', 3000);
  //       setTimeout(() => this.router.navigate(['/produits']), 3000);
  //     } catch (error) {
  //       console.error('Error saving produit:', error);
  //       this.errorMessage = 'Erreur lors de l\'enregistrement du produit.';
  //       setTimeout(() => this.errorMessage = '', 3000);
  //     }
  //   } else {
  //     console.error('Category must be selected');
  //     this.errorMessage = 'La catégorie doit être sélectionnée.';
  //     setTimeout(() => this.errorMessage = '', 3000);
  //   }
  // }
  onSubmit(): void {
    const currentUser = this.authService.currentUserValue;
    if (!currentUser || !currentUser.email) {
      this.errorMessage = 'Erreur: email utilisateur non trouvé';
      return;
    }
    const email = currentUser.email;
  
    if (this.selectedCategoryId !== null) {
      this.produit.categorie = { id: this.selectedCategoryId } as Categorie;
  
      this.produitService.createProduit(this.produit, email).subscribe((result) => {
        this.qrCodeUrl = result.qrCode; 
        this.successMessage = 'Produit ajouté avec succès!';
        setTimeout(() => this.successMessage = '', 2000);
        setTimeout(() => this.router.navigate(['/produits']), 2000);
      }, error => {
        console.error('Erreur lors de l\'ajout du produit:', error);
        this.errorMessage = 'Erreur lors de l\'ajout du produit.';
        setTimeout(() => this.errorMessage = '', 2000);
      });
    } else {
      this.errorMessage = 'La catégorie doit être sélectionnée.';
      setTimeout(() => this.errorMessage = '', 2000);
    }
  }
  

  // onSubmit(): void {
  //   const currentUser = this.authService.currentUserValue;
  //   if (!currentUser || !currentUser.email) {
  //     this.errorMessage = 'Erreur: email utilisateur non trouvé';
  //     return;
  //   }
  //   const email = currentUser.email;

  //   if (this.selectedCategoryId !== null) {
  //     this.produit.categorie = { id: this.selectedCategoryId } as Categorie;

  //     this.produitService.createProduit(this.produit, email).subscribe(() => {
  //       this.successMessage = 'Produit ajouté avec succès!';
  //       setTimeout(() => this.successMessage = '', 2000);
  //       setTimeout(() => this.router.navigate(['/produits']), 2000);
  //     }, error => {
  //       console.error('Erreur lors de l\'ajout du produit:', error);
  //       this.errorMessage = 'Erreur lors de l\'ajout du produit.';
  //       setTimeout(() => this.errorMessage = '', 2000);
  //     });
  //   } else {
  //     this.errorMessage = 'La catégorie doit être sélectionnée.';
  //     setTimeout(() => this.errorMessage = '', 2000);
  //   }
  // }

  navigateToBonEntree() {
    this.router.navigate(['/produits']);
  }
}
