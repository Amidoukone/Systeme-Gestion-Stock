import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, Router } from '@angular/router';
import { FournisseurService } from '../../../services/fournisseur.service';
import { Fournisseur } from '../../../models/fournisseur';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-fournisseur-form',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './fournisseur-form.component.html',
  styleUrls: ['./fournisseur-form.component.css']
})
export class FournisseurFormComponent implements OnInit {

  fournisseur: Fournisseur = new Fournisseur();
  isEditMode: boolean = false;
  successMessage: string = '';
  errorMessage: string = '';

  constructor(
    private fournisseurService: FournisseurService,
    private route: ActivatedRoute,
    private router: Router
  ) { }

  ngOnInit(): void {
    const id = this.route.snapshot.paramMap.get('id');
    if (id) {
      this.isEditMode = true;
      this.loadFournisseurById(+id);
    }
  }

  loadFournisseurById(id: number): void {
    this.fournisseurService.getFournisseurById(id).subscribe(data => {
      this.fournisseur = data;
    }, error => {
      console.error('Error loading fournisseur:', error);
      this.errorMessage = 'Erreur lors du chargement du fournisseur.';
      setTimeout(() => this.errorMessage = '', 3000);
    });
  }

  onSubmit(): void {
    if (!this.fournisseur.fournName) {
      this.errorMessage = 'Nom du fournisseur est requis.';
      setTimeout(() => this.errorMessage = '', 3000);
      return;
    }

    if (this.isEditMode) {
      this.fournisseurService.updateFournisseur(this.fournisseur.id, this.fournisseur).subscribe(() => {
        this.successMessage = 'Fournisseur mis à jour avec succès!';
        setTimeout(() => this.successMessage = '', 3000);
        setTimeout(() => this.router.navigate(['/fournisseurs']), 3000);
      }, error => {
        console.error('Error updating fournisseur:', error);
        this.errorMessage = 'Erreur lors de la mise à jour du fournisseur.';
        setTimeout(() => this.errorMessage = '', 3000);
      });
    } else {
      this.fournisseurService.createFournisseur(this.fournisseur).subscribe(() => {
        this.successMessage = 'Fournisseur ajouté avec succès!';
        setTimeout(() => this.successMessage = '', 3000);
        setTimeout(() => this.router.navigate(['/fournisseurs']), 3000);
      }, error => {
        console.error('Error creating fournisseur:', error);
        this.errorMessage = 'Erreur lors de l\'ajout du fournisseur.';
        setTimeout(() => this.errorMessage = '', 3000);
      });
    }
  }
}
