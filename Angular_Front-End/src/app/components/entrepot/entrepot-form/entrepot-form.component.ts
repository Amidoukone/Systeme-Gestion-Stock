import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, Router } from '@angular/router';
import { EntrepotService } from '../../../services/entrepot.service';
import { Entrepot } from '../../../models/entrepot';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-entrepot-form',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './entrepot-form.component.html',
  styleUrls: ['./entrepot-form.component.css']
})
export class EntrepotFormComponent implements OnInit {
  entrepot: Entrepot = {} as Entrepot;
  isEditMode: boolean = false;
  successMessage: string = '';
  errorMessage: string = '';

  constructor(
    private entrepotService: EntrepotService,
    private route: ActivatedRoute,
    private router: Router
  ) { }

  ngOnInit(): void {
    const id = this.route.snapshot.paramMap.get('id');
    if (id) {
      this.isEditMode = true;
      this.loadEntrepotById(+id);
    }
  }

  loadEntrepotById(id: number) {
    this.entrepotService.getEntrepotById(id).subscribe(data => {
      this.entrepot = data;
    }, error => {
      console.error('Error loading entrepot:', error);
      this.errorMessage = 'Erreur lors du chargement de l\'entrepôt.';
      setTimeout(() => this.errorMessage = '', 3000);
    });
  }

  onSubmit(event: Event): void {
    event.preventDefault();

    if (this.isEditMode) {
      this.entrepotService.updateEntrepot(this.entrepot.id, this.entrepot).subscribe(() => {
        this.successMessage = 'Entrepôt mis à jour avec succès!';
        setTimeout(() => this.successMessage = '', 3000);
        setTimeout(() => this.router.navigate(['/entrepots']), 3000);
      }, error => {
        console.error('Error updating entrepot:', error);
        this.errorMessage = 'Erreur lors de la mise à jour de l\'entrepôt.';
        setTimeout(() => this.errorMessage = '', 3000);
      });
    } else {
      this.entrepotService.createEntrepot(this.entrepot).subscribe(() => {
        this.successMessage = 'Entrepôt ajouté avec succès!';
        setTimeout(() => this.successMessage = '', 3000);
        setTimeout(() => this.router.navigate(['/entrepots']), 3000);
      }, error => {
        console.error('Error creating entrepot:', error);
        this.errorMessage = 'Erreur lors de l\'ajout de l\'entrepôt.';
        setTimeout(() => this.errorMessage = '', 3000);
      });
    }
  }
}
