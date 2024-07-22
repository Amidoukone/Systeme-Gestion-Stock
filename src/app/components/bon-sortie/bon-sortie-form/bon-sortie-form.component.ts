import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, Router } from '@angular/router';
import { BonSortieService } from '../../../services/bon-sortie.service';
import { DetailSortieService } from '../../../services/detail-sortie.service';
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
    const id = this.route.snapshot.paramMap.get('id');
    if (id) {
      this.isEditMode = true;
      this.bonSortieService.getBonSortieById(Number(id)).subscribe(bonSortie => {
        this.bonSortie = bonSortie;
        this.detailsSorties = bonSortie.detailsSorties || [];
      });
    }
  }

/*onUtilisateurChange(event: any): void {
  this.selectedUtilisateurId = event.target.value;
}

addDetailSortie(): void {
  this.detailsSorties.push(new DetailSortie());
}

onSubmit(): void {
  this.bonSortie.detailsSorties = this.detailsSorties;
  if (this.isEditMode) {
    this.bonSortieService.updateBonSortie(this.bonSortie.id, this.bonSortie).subscribe(() => {
      this.router.navigate(['/bon-sorties']);
    });
  } else {
    this.bonSortieService.createBonSortie(this.bonSortie).subscribe(() => {
      this.router.navigate(['/bon-sorties']);
    });
  }
}*/
}

