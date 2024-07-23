import {Component, NO_ERRORS_SCHEMA, OnInit} from '@angular/core';
import { CommonModule } from '@angular/common';
import { CategorieService } from '../../../services/categorie.service';
import { Categorie } from '../../../models/categorie';
import {HttpClient} from "@angular/common/http";
import {Router} from "@angular/router";

@Component({
  selector: 'app-categorie-list',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './categorie-list.component.html',
  styleUrl: './categorie-list.component.css'
})
export class CategorieListComponent implements OnInit {
  categories: Categorie[] = [];

  constructor(private categorieService: CategorieService, private router: Router) { }

  ngOnInit(): void {
    this.loadCategories();
  }

  loadCategories(): void {
    this.categorieService.getCategories().subscribe(data => {
      this.categories = data;
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
}
