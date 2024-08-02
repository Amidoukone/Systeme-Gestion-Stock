import {Component, NO_ERRORS_SCHEMA, OnInit} from '@angular/core';
import { CommonModule } from '@angular/common';
import { CategorieService } from '../../../services/categorie.service';
import { Categorie } from '../../../models/categorie';
import {HttpClient} from "@angular/common/http";
import {Router, RouterLink} from "@angular/router";
import { AuthService } from "../../../services/auth.service"
import {NgbModal, NgbModalRef} from "@ng-bootstrap/ng-bootstrap";
import {NgxPaginationModule} from "ngx-pagination";

@Component({
  selector: 'app-categorie-list',
  standalone: true,
  imports: [CommonModule, NgxPaginationModule, RouterLink],
  templateUrl: './categorie-list.component.html',
  styleUrl: './categorie-list.component.css'
})
export class CategorieListComponent implements OnInit {
  categories: Categorie[] = [];
  errorMessage: string = '';

  page: number = 1;
  itemsPerPage: number = 6;  // Nombre d'éléments par page

  categoriesToDelete: number | null = null;
  private modalRef: NgbModalRef | null = null;
  infoMessage: string = '';

  constructor(private categorieService: CategorieService, private router: Router, private authService: AuthService, private modalService: NgbModal) { }

  ngOnInit(): void {
    const currentUser = this.authService.currentUserValue;
    if (!currentUser || !currentUser.email) {
      this.errorMessage = 'Erreur: email utilisateur non trouvé';
      return;
    }
    const email = currentUser.email;
  
    this.categorieService.getCategoriesForCurrentUser(email).subscribe(categories => {
      if (categories.length === 0) {
        this.infoMessage = 'Aucune catégorie trouvée pour cet Entrepot.';
        setTimeout(() => this.infoMessage = '', 2000);
      } else {
        this.categories = categories;
      }
    }, error => {
        console.error('Erreur lors de la récupération des catégories:', error);
        this.errorMessage = 'Erreur lors de la récupération des catégories.';
      });
    
  }
  
  addCategorie(): void {
    this.router.navigate(['/add-categorie']);
  }

  editCategorie(id: number): void {
    this.router.navigate(['/edit-categorie', id]);
  }

  deleteCategorie(id: number): void {
    this.categorieService.deleteCategorie(id).subscribe(() => {
      this.categories = this.categories.filter(categorie => categorie.id !== id);
    });
  }


  showDeleteConfirmation(content: any, id: number): void {
    this.categoriesToDelete = id;
    this.modalRef = this.modalService.open(content);
  }

  confirmDelete(): void {
    if (this.categoriesToDelete !== null) {
      this.categorieService.deleteCategorie(this.categoriesToDelete).subscribe(() => {
        this.categories = this.categories.filter(f => f.id !== this.categoriesToDelete);
        this.categoriesToDelete = null;
        this.modalRef?.close();
      });
    }
  }


}
