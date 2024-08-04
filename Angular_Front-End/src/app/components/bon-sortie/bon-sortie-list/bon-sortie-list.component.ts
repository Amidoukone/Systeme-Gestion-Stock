import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { Router, RouterModule } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { NgxPaginationModule } from 'ngx-pagination';
import { BonSortie } from '../../../models/bon-sortie';
import { AuthService } from '../../../services/auth.service';
import { BonSortieService } from '../../../services/bon-sortie.service';

@Component({
  selector: 'app-bon-sortie-list',
  templateUrl: './bon-sortie-list.component.html',
  styleUrls: ['./bon-sortie-list.component.css'],
  standalone: true,
  imports: [CommonModule, RouterModule, NgxPaginationModule]
})
export class BonSortieListComponent implements OnInit {
  bonSorties: BonSortie[] = [];
  filteredBonSorties: BonSortie[] = [];
  bonSortieToDelete: number | null = null;
  private modalRef: NgbModalRef | null = null;
  selectedBonSortie: BonSortie | null = null;

  page: number = 1;
  itemsPerPage: number = 6;
  infoMessage= '';
  errorMessage= '';

  constructor(
    private bonSortieService: BonSortieService,
    private modalService: NgbModal,
    private router: Router,
    private authService: AuthService
  ) {}

  ngOnInit(): void {
    this.loadBonSorties();
  }

  loadBonSorties(): void {
    const currentUser = this.authService.currentUserValue;
    if (currentUser && currentUser.entrepot) {
      const entrepotId = currentUser.entrepot.entrepotId;
      this.bonSortieService.getBonSortiesByEntrepots(entrepotId).subscribe(data => {
        console.log('Données reçues:', data); // Debugging
        if (data.length === 0) {
          this.infoMessage = 'Aucun Bon Entrees trouvée pour cet Entrepot.';
          setTimeout(() => this.infoMessage = '', 2000);
        } else {
          this.bonSorties = data;
          this.filteredBonSorties = data;
        }
      }, error => {
        console.error('Erreur lors de la récupération des Bon Entrees:', error);
        this.errorMessage = 'Erreur lors de la récupération des Bon Entrees.';
      });
    } else {
      this.errorMessage = 'Erreur: entrepôt utilisateur non trouvé';
    }
  }
  

  applyFilter(event: Event): void {
    const filterValue = (event.target as HTMLInputElement).value.trim().toLowerCase();
    this.filteredBonSorties = this.bonSorties.filter(bonSortie =>
      bonSortie.motif && bonSortie.motif.title && bonSortie.motif.title.toLowerCase().includes(filterValue)
    );
  }

  deleteBonSortie(id: number): void {
    this.bonSortieService.deleteBonSortie(id).subscribe(() => {
      this.bonSorties = this.bonSorties.filter(b => b.id !== id);
      this.filteredBonSorties = this.filteredBonSorties.filter(b => b.id !== id);
    });
  }

  printBonSortie(id: number): void {
    this.router.navigate(['/print-bon-sortie', id]);
  }

  navigateToAddDetail(bonSortieId: number): void {
    this.router.navigate(['/bon-sortie-detail', bonSortieId]);
  }

  showDeleteConfirmation(content: any, id: number): void {
    this.bonSortieToDelete = id;
    this.modalRef = this.modalService.open(content);
  }

  confirmDelete(): void {
    if (this.bonSortieToDelete !== null) {
      this.bonSortieService.deleteBonSortie(this.bonSortieToDelete).subscribe(() => {
        this.bonSorties = this.bonSorties.filter(f => f.id !== this.bonSortieToDelete);
        this.filteredBonSorties = this.filteredBonSorties.filter(f => f.id !== this.bonSortieToDelete);
        this.bonSortieToDelete = null;
        this.modalRef?.close();
      });
    }
  }

  openDetailsModal(content: any, bonSortie: BonSortie): void {
    this.selectedBonSortie = bonSortie;
    this.modalRef = this.modalService.open(content);
  }

  hasDetails(bonSortie: BonSortie): boolean {
    return bonSortie.detailsSorties && bonSortie.detailsSorties.length > 0;
  }
  hasRole(role: string): boolean {
    return this.authService.hasRole(role);
  }
}
