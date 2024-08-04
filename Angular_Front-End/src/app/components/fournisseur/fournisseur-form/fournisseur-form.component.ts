import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, Router } from '@angular/router';
import { FournisseurService } from '../../../services/fournisseur.service';
import { Fournisseur } from '../../../models/fournisseur';
import { FormsModule } from '@angular/forms';
import { AuthService } from '../../../services/auth.service';

@Component({
  selector: 'app-fournisseur-form',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './fournisseur-form.component.html',
  styleUrls: ['./fournisseur-form.component.css']
})
export class FournisseurFormComponent implements OnInit {
  isEditMode: boolean = false;
  successMessage: string = '';
  errorMessage: string = '';
  fournisseur: Fournisseur = {} as Fournisseur;

  constructor(
    private fournisseurService: FournisseurService,
    private route: ActivatedRoute,
    private router: Router,
    private authService: AuthService
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
      setTimeout(() => this.errorMessage = '', 2000);
    });
  }

  onSubmit(): void {
    const currentUser = this.authService.currentUserValue;
    if (!currentUser || !currentUser.email) {
      this.errorMessage = 'Erreur: email utilisateur non trouvé';
      return;
    }
    const email = currentUser.email;

    if (this.isEditMode) {
      this.fournisseurService.updateFournisseur(this.fournisseur.id, this.fournisseur).subscribe(() => {
        this.successMessage = 'Fournisseur mis à jour avec succès!';
        setTimeout(() => this.successMessage = '', 2000);
        setTimeout(() => this.router.navigate(['/fournisseurs']), 2000);
      }, error => {
        console.error('Error updating fournisseur:', error);
        this.errorMessage = 'Erreur lors de la mise à jour du fournisseur.';
        setTimeout(() => this.errorMessage = '', 2000);
      });
    } else {
      this.fournisseurService.createFournisseur(this.fournisseur, email).subscribe(() => {
        this.successMessage = 'Fournisseur ajouté avec succès!';
        setTimeout(() => this.successMessage = '', 2000);
        setTimeout(() => this.router.navigate(['/fournisseurs']), 2000);
      }, error => {
        console.error('Error creating fournisseur:', error);
        this.errorMessage = 'Erreur lors de l\'ajout du fournisseur.';
        setTimeout(() => this.errorMessage = '', 2000);
      });
    }
  }


  navigateToBonEntree() {
    this.router.navigate(['/fournisseurs']);
  }
}
