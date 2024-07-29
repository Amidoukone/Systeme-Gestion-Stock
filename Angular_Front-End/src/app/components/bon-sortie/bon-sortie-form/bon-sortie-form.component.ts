import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, Router } from '@angular/router';
import { BonSortieService } from '../../../services/bon-sortie.service';
import { BonSortie } from '../../../models/bon-sortie';
import { FormsModule } from '@angular/forms';
import { DetailSortie } from '../../../models/detail-sortie';
import { ProduitService } from '../../../services/produit.service';
import { AuthService } from '../../../services/auth.service';
import { format } from 'date-fns';
import { Motif } from '../../../models/motif';
import { MotifService } from '../../../services/motif.service';
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
    });
  }

  loadBonSortieById(id: number): void {
    this.bonSortieService.getBonSortieById(id).subscribe(data => {
      this.bonSortie = data;
      this.selectedMotifId = data.motif.id;
    });
  }

  onSubmit(): void {
    this.bonSortie.detailsSorties = this.detailSortie;
    this.bonSortie.motif = {id: this.selectedMotifId, title: this.selectedMotifId.title , createBy: this.authService.currentUserValue?.id } as Motif;

    const formattedBonEntree = {
      ...this.bonSortie,
      date_commande: this.bonSortie.dateSortie
        ? format(new Date(this.bonSortie.dateSortie), 'yyyy-MM-dd')
        : null
    };

    console.log(this.bonSortie);
    if (this.isEditMode) {
      this.bonSortieService.updateBonSortie(this.bonSortie.id, this.bonSortie).subscribe(() => {
        this.router.navigate(['/bon-sortie']);
      }, error => {
        console.error('Error updating bon sortie:', error);
      });
    } else {
      this.bonSortieService.createBonSortie(this.bonSortie).subscribe(() => {
        this.router.navigate(['/bon-sortie']);
      }, error => {
        console.error('Error creating bon sortie:', error);
      });
    }
  }
}
