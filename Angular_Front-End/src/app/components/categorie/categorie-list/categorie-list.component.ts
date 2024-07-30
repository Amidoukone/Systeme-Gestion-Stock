import {Component, NO_ERRORS_SCHEMA, OnInit} from '@angular/core';
import { CommonModule } from '@angular/common';
import { CategorieService } from '../../../services/categorie.service';
import { Categorie } from '../../../models/categorie';
import {HttpClient} from "@angular/common/http";
import {Router} from "@angular/router";
import { AuthService } from "../../../services/auth.service"
import {NgbModal, NgbModalRef} from "@ng-bootstrap/ng-bootstrap";

@Component({
  selector: 'app-categorie-list',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './categorie-list.component.html',
  styleUrl: './categorie-list.component.css'
})
export class CategorieListComponent implements OnInit {
  categories: Categorie[] = [];

  categoriesToDelete: number | null = null;
  private modalRef: NgbModalRef | null = null;

  constructor(private categorieService: CategorieService, private router: Router, private authService: AuthService, private modalService: NgbModal) { }

  ngOnInit(): void {
    this.loadCategories();
  }

  loadCategories(): void {
    this.categorieService.getCategories().subscribe(data => {
      this.categories = data.map(category => ({
        ...category,
        createBy: this.authService.currentUserValue?.id
      }));
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
