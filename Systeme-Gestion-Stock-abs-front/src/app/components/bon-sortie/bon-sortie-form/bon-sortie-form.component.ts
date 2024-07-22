import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, Router } from '@angular/router';
import { BonSortieService } from '../../../services/bon-sortie.service';
import { UtilisateurService } from '../../../services/utilisateur.service';
import { BonSortie } from '../../../models/bon-sortie';
import { Utilisateur } from '../../../models/utilisateur';
import { FormsModule } from '@angular/forms';
import { DetailSortie } from '../../../models/detail-sortie';
import { Produit } from '../../../models/produit';
@Component({
  selector: 'app-bon-sortie-form',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './bon-sortie-form.component.html',
  styleUrl: './bon-sortie-form.component.css'
})
export class BonSortieFormComponent implements OnInit {
  bonSortie: BonSortie = {} as BonSortie;
  utilisateurs: Utilisateur[] = [];
  produits: Produit[] = [];
  detailsSorties: DetailSortie[] = [];
  isEditMode: boolean = false;
  selectedUtilisateurId: number | null = null;

  constructor(
    private bonSortieService: BonSortieService,
    private utilisateurService: UtilisateurService,
    private route: ActivatedRoute,
    private router: Router
  ) { }

  ngOnInit(): void {
    this.loadUtilisateurs();

    const id = this.route.snapshot.paramMap.get('id');
    if (id) {
      this.isEditMode = true;
      this.loadBonSortieById(+id);
    }
  }

  async loadUtilisateurs() {
    try {
      this.utilisateurs = await this.utilisateurService.getUtilisateurs().toPromise() || [];
      console.log('Utilisateurs loaded:', this.utilisateurs);
    } catch (error) {
      console.error('Error loading utilisateurs:', error);
    }
  }

  async loadBonSortieById(id: number) {
    try {
      const bonSortie = await this.bonSortieService.getBonSortieById(id).toPromise();
      if (bonSortie) {
        this.bonSortie = bonSortie;
        this.selectedUtilisateurId = bonSortie.utilisateur_id;
        this.detailsSorties = bonSortie.detailSorties || [];
        console.log('BonSortie loaded:', this.bonSortie);
      }
    } catch (error) {
      console.error('Error loading bonSortie:', error);
    }
  }

  onUtilisateurChange(event: any): void {
    this.selectedUtilisateurId = event.value;
  }

  addDetailSortie(): void {
    this.detailsSorties.push({
      details_sortie_id: 0,
      produit_id: 0,
      quantity: 0,
      prix_total: 0,
      bon_sortie_id: this.bonSortie.id
    } as DetailSortie);
  }

  async onSubmit(event: Event): Promise<void> {
    event.preventDefault();

    if (this.selectedUtilisateurId !== null) {
      this.bonSortie.utilisateur_id = this.selectedUtilisateurId;
      this.bonSortie.detailSorties = this.detailsSorties;

      try {
        const formattedBonSortie = {
          ...this.bonSortie,
          date_commande: this.bonSortie.dateCommande.toISOString().split('T')[0]  // Ensure date is in correct format
        };

        if (this.isEditMode) {
          await this.bonSortieService.updateBonSortie(this.bonSortie.id, formattedBonSortie).toPromise();
        } else {
          await this.bonSortieService.createBonSortie(formattedBonSortie).toPromise();
        }
        this.router.navigate(['/bon-sorties']);
      } catch (error) {
        console.error('Error saving bonSortie:', error);
      }
    } else {
      console.error('Utilisateur must be selected');
    }
  }
}
