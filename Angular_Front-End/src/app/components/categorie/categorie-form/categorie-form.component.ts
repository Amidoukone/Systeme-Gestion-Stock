import { Component, OnInit, NO_ERRORS_SCHEMA } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, Router } from '@angular/router';
import { CategorieService } from '../../../services/categorie.service';
import { Categorie } from '../../../models/categorie';
import { FormsModule } from '@angular/forms';
import { AuthService} from "../../../services/auth.service";

@Component({
  selector: 'app-categorie-form',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './categorie-form.component.html',
  styleUrl: './categorie-form.component.css'
})
export class CategorieFormComponent implements OnInit {
  categorie: Categorie = { id: 0, name: '', createBy: 0 };
  isEditMode: boolean = false;

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
      this.categorie.createBy = this.authService.currentUserValue.id;
      this.categorie = data;
    });
  }

  onSubmit(): void {
    if (this.isEditMode) {
      this.categorieService.updateCategorie(this.categorie.id, this.categorie).subscribe(() => {
        this.router.navigate(['/categories']);
      });
    } else {
      this.categorieService.createCategorie(this.categorie).subscribe(() => {
        this.router.navigate(['/categories']);
      });
    }
  }
}
