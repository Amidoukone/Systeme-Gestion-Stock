import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, Router } from '@angular/router';
import { BonSortieService } from '../../../services/bon-sortie.service';
import { BonSortie } from '../../../models/bon-sortie';
import { FormsModule } from '@angular/forms';
import { DetailSortie } from '../../../models/detail-sortie';
import { MotifService } from '../../../services/motif.service';
import { Motif } from '../../../models/motif';
import { AuthService } from '../../../services/auth.service';
import { format } from 'date-fns';
import { Utilisateur } from '../../../models/utilisateur';

@Component({
  selector: 'app-bon-sortie-form',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './bon-sortie-form.component.html',
  styleUrls: ['./bon-sortie-form.component.css']
})
export class BonSortieFormComponent implements OnInit {
  bonSortie: BonSortie = {} as BonSortie;
  motifs: Motif[] = [];
  detailSortie: DetailSortie[] = [];
  selectedMotifId: number | any;
  isEditMode: boolean = false;
  successMessage: string = '';
  errorMessage: string = '';

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private bonSortieService: BonSortieService,
    private authService: AuthService,
    private motifService: MotifService
  ) { }

  ngOnInit(): void {
    this.bonSortie.utilisateur = {
      id: this.authService.currentUserValue?.id,
      username: this.authService.currentUserValue?.username
    } as Utilisateur;
    this.loadMotifs();
    const id = this.route.snapshot.paramMap.get('id');
    if (id) {
      this.isEditMode = true;
      this.loadBonSortieById(+id);
    }
  }

  loadMotifs(): void {
    this.motifService.getMotifs().subscribe(data => {
      this.motifs = data;
      console.log('Motifs reçus:', data); // Ajoutez cette ligne

    });
  }

  loadBonSortieById(id: number): void {
    this.bonSortieService.getBonSortieById(id).subscribe(data => {
      this.bonSortie = data;
      this.selectedMotifId = data.motif ? data.motif.id : null;
    }, error => {
      console.error('Error loading bon de sortie:', error);
      this.errorMessage = 'Erreur lors du chargement du bon de sortie.';
      setTimeout(() => this.errorMessage = '', 3000);
    });
  }

  onSubmit(): void {
    const selectedMotif = this.motifs.find(motif => motif.id === this.selectedMotifId);
    console.log('Motif sélectionné:', selectedMotif);
    this.bonSortie.detailsSorties = this.detailSortie;
    this.bonSortie.motif = {id: this.selectedMotifId} as Motif;

    const formattedBonSortie = {
      ...this.bonSortie,
      date_sortie: this.bonSortie.dateSortie
        ? format(new Date(this.bonSortie.dateSortie), 'yyyy-MM-dd')
        : null
    };

    if (this.isEditMode) {
      this.bonSortieService.updateBonSortie(this.bonSortie.id, formattedBonSortie).subscribe(() => {
        this.successMessage = 'Bon de Sortie mis à jour avec succès!';
        setTimeout(() => this.successMessage = '', 3000);
        setTimeout(() => this.router.navigate(['/bon-sortie']), 3000);
      }, error => {
        console.error('Error updating bon de sortie:', error);
        this.errorMessage = 'Erreur lors de la mise à jour du bon de sortie.';
        setTimeout(() => this.errorMessage = '', 3000);
      });
    } else {
      this.bonSortieService.createBonSortie(formattedBonSortie).subscribe(() => {
        this.successMessage = 'Bon de Sortie créé avec succès!';
        setTimeout(() => this.successMessage = '', 3000);
        setTimeout(() => this.router.navigate(['/bon-sortie']), 3000);
      }, error => {
        console.error('Error creating bon de sortie:', error);
        this.errorMessage = 'Erreur lors de la création du bon de sortie.';
        setTimeout(() => this.errorMessage = '', 3000);
      });
    }
  }

  navigateToBonEntree() {
    this.router.navigate(['/bon-sortie']);
  }
}
