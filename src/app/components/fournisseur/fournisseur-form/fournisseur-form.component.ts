import { Component, OnInit, NO_ERRORS_SCHEMA } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, Router } from '@angular/router';
import { FournisseurService } from '../../../services/fournisseur.service';
import { Fournisseur } from '../../../models/fournisseur'
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-fournisseur-form',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './fournisseur-form.component.html',
  styleUrl: './fournisseur-form.component.css'
})
export class FournisseurFormComponent implements OnInit{

  fournisseur: Fournisseur = new Fournisseur();
  isEditMode: boolean = false;

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
    });
  }

  onSubmit(): void {
    if (!this.fournisseur.fournName) {
      console.error('Nom du fournisseur est requis');
      return;
    }

    if (this.isEditMode) {
      this.fournisseurService.updateFournisseur(this.fournisseur.id, this.fournisseur).subscribe(() => {
        this.router.navigate(['/fournisseurs']);
      });
    } else {
      this.fournisseurService.createFournisseur(this.fournisseur).subscribe(() => {
        this.router.navigate(['/fournisseurs']);
      });
    }
  }
}
