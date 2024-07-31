import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, Router } from '@angular/router';
import { CategorieService } from '../../../services/categorie.service';
import { Categorie } from '../../../models/categorie';
import { FormsModule } from '@angular/forms';
import { AuthService } from '../../../services/auth.service';

@Component({
  selector: 'app-categorie-form',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './categorie-form.component.html',
  styleUrls: ['./categorie-form.component.css']
})
export class CategorieFormComponent implements OnInit {
  categorie: Categorie = { id: 0, name: '', createBy: 0 };
  isEditMode: boolean = false;
  successMessage: string = '';
  errorMessage: string = '';

  constructor(
    private categorieService: CategorieService,
    private route: ActivatedRoute,
    private router: Router,
    private authService: AuthService
  ) { }

  ngOnInit(): void {
    const id = this.route.snapshot.paramMap.get('id');
    if (id) {
      this.isEditMode = true;
      this.loadCategorieById(+id);
    }
  }

  loadCategorieById(id: number): void {
    this.categorieService.getCategorieById(id).subscribe(data => {
      this.categorie = data;
      this.categorie.createBy = this.authService.currentUserValue.id;
    }, error => {
      console.error('Error loading categorie:', error);
      this.errorMessage = 'Erreur lors du chargement de la catégorie.';
      setTimeout(() => this.errorMessage = '', 3000);
    });
  }

  onSubmit(): void {
    this.categorie.createBy = this.authService.currentUserValue.id;
    
    if (this.isEditMode) {
      this.categorieService.updateCategorie(this.categorie.id, this.categorie).subscribe(() => {
        this.successMessage = 'Catégorie mise à jour avec succès!';
        setTimeout(() => this.successMessage = '', 3000);
        setTimeout(() => this.router.navigate(['/categories']), 3000);
      }, error => {
        console.error('Error updating categorie:', error);
        this.errorMessage = 'Erreur lors de la mise à jour de la catégorie.';
        setTimeout(() => this.errorMessage = '', 3000);
      });
    } else {
      this.categorieService.createCategorie(this.categorie).subscribe(() => {
        this.successMessage = 'Catégorie ajoutée avec succès!';
        setTimeout(() => this.successMessage = '', 3000);
        setTimeout(() => this.router.navigate(['/categories']), 3000);
      }, error => {
        console.error('Error creating categorie:', error);
        this.errorMessage = 'Erreur lors de l\'ajout de la catégorie.';
        setTimeout(() => this.errorMessage = '', 3000);
      });
    }
  }
}
