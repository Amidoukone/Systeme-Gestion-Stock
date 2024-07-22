import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { BonSortieService } from '../../../services/bon-sortie.service';
import { BonSortie } from '../../../models/bon-sortie';
import { RouterModule } from '@angular/router';

@Component({
  selector: 'app-bon-sortie-list',
  standalone: true,
  imports: [CommonModule, RouterModule],
  templateUrl: './bon-sortie-list.component.html',
  styleUrls: ['./bon-sortie-list.component.css']
})
export class BonSortieListComponent implements OnInit {
  bonSorties: BonSortie[] = [];
  filteredBonSorties: BonSortie[] = [];

  constructor(private bonSortieService: BonSortieService) {}

  ngOnInit(): void {

  }

/*loadBonSorties(): void {
  this.bonSortieService.getBonSorties().subscribe(data => {
    this.bonSorties = data;
    this.filteredBonSorties = data;
  });
}

applyFilter(event: Event): void {
  const filterValue = (event.target as HTMLInputElement).value.toLowerCase();
  this.filteredBonSorties = this.bonSorties.filter(bonSortie => bonSortie.statut.toLowerCase().includes(filterValue));
}

deleteBonSortie(id: number): void {
  this.bonSortieService.deleteBonSortie(id).subscribe(() => {
    this.loadBonSorties();
  });
}

printBonSortie(id: number): void {
  // Imprimer le bon de sortie
}*/
}
