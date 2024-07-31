import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { MotifService } from '../../../services/motif.service';
import { Motif } from '../../../models/motif';
import { FormsModule } from '@angular/forms';
import { CommonModule } from "@angular/common";
import { AuthService } from "../../../services/auth.service";

@Component({
  selector: 'app-motif-form',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './motif-form.component.html',
  styleUrls: ['./motif-form.component.css']
})
export class MotifFormComponent implements OnInit {
  motif: Motif = {} as Motif;
  isEditMode: boolean = false;
  successMessage: string = '';
  errorMessage: string = '';

  constructor(
    private motifService: MotifService,
    private route: ActivatedRoute,
    private router: Router,
    private authService: AuthService
  ) { }

  ngOnInit(): void {
    const id = this.route.snapshot.paramMap.get('id');
    if (id) {
      this.isEditMode = true;
      this.loadMotifById(+id);
    }
  }

  loadMotifById(id: number): void {
    this.motifService.getMotifById(id).subscribe(data => {
      this.motif = data;
      console.log('Motif loaded:', this.motif);
    }, error => {
      console.error('Error loading motif:', error);
      this.errorMessage = 'Erreur lors du chargement du motif.';
      setTimeout(() => this.errorMessage = '', 3000);
    });
  }

  onSubmit(): void {
    this.motif.createBy = this.authService.currentUserValue.id;
    if (this.isEditMode) {
      this.motifService.updateMotif(this.motif.id, this.motif).subscribe(() => {
        this.successMessage = 'Motif mis à jour avec succès!';
        setTimeout(() => this.successMessage = '', 3000);
        setTimeout(() => this.router.navigate(['/motif']), 3000);
      }, error => {
        console.error('Error updating motif:', error);
        this.errorMessage = 'Erreur lors de la mise à jour du motif.';
        setTimeout(() => this.errorMessage = '', 3000);
      });
    } else {
      this.motifService.createMotif(this.motif).subscribe(() => {
        this.successMessage = 'Motif ajouté avec succès!';
        setTimeout(() => this.successMessage = '', 3000);
        setTimeout(() => this.router.navigate(['/motif']), 3000);
      }, error => {
        console.error('Error creating motif:', error);
        this.errorMessage = 'Erreur lors de l\'ajout du motif.';
        setTimeout(() => this.errorMessage = '', 3000);
      });
    }
  }
}
