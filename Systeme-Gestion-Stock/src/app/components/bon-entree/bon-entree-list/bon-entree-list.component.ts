import {Component, NO_ERRORS_SCHEMA, OnInit} from '@angular/core';
import { CommonModule } from '@angular/common';
import { BonEntreeService } from '../../../services/bon-entree.service';
import { BonEntree } from '../../../models/bon-entree';
import {HttpClient} from "@angular/common/http";
import {Router} from "@angular/router";

@Component({
  selector: 'app-bon-entree-list',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './bon-entree-list.component.html',
  styleUrl: './bon-entree-list.component.css',
  schemas: [ NO_ERRORS_SCHEMA ]
})
export class BonEntreeListComponent implements OnInit {
  bonEntrees: BonEntree[] = [];
  filteredBonEntrees: BonEntree[] = [];

  constructor(private bonEntreeService: BonEntreeService) { }

  ngOnInit(): void {
    this.loadBonEntrees();
  }

  loadBonEntrees(): void {
    this.bonEntreeService.getBonEntrees().subscribe(data => {
      this.bonEntrees = data;
      this.filteredBonEntrees = data;
    });
  }

  applyFilter(event: Event): void {
    const filterValue = (event.target as HTMLInputElement).value.trim().toLowerCase();
    this.filteredBonEntrees = this.bonEntrees.filter(bonEntree =>
      bonEntree.statut.toLowerCase().includes(filterValue)
    );
  }

  deleteBonEntree(id: number): void {
    this.bonEntreeService.deleteBonEntree(id).subscribe(() => {
      this.bonEntrees = this.bonEntrees.filter(b => b.id !== id);
      this.filteredBonEntrees = this.filteredBonEntrees.filter(b => b.id !== id);
    });
  }

  printBonEntree(id: number): void {
    // Implémentez la logique d'impression ici
  }
}
