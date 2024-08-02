import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { Router, RouterModule } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { NgxPaginationModule } from 'ngx-pagination';
import { BonEntree } from '../../../models/bon-entree';
import { BonEntreeService } from '../../../services/bon-entree.service';

@Component({
  selector: 'app-bon-entree-list',
  templateUrl: './bon-entree-list.component.html',
  styleUrls: ['./bon-entree-list.component.css'],
  standalone: true,
  imports: [CommonModule, RouterModule, NgxPaginationModule]
})
export class BonEntreeListComponent implements OnInit {
  bonEntrees: BonEntree[] = [];
  filteredBonEntrees: BonEntree[] = [];
  bonentreeToDelete: number | null = null;
  bonentreeToValidate: number | null = null;  
  private modalRef: NgbModalRef | null = null;
  selectedBonEntree: BonEntree | null = null;

  page: number = 1;
  itemsPerPage: number = 6;  

  constructor(
    private bonEntreeService: BonEntreeService,
    private modalService: NgbModal,
    private router: Router
  ) {}

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
    this.router.navigate(['/print-bon-entree', id]);
  }

  navigateToAddDetail(bonEntreeId: number): void {
    this.router.navigate(['/bon-entree-detail', bonEntreeId]);
  }

  showDeleteConfirmation(content: any, id: number): void {
    this.bonentreeToDelete = id;
    this.modalRef = this.modalService.open(content);
  }

  confirmDelete(): void {
    if (this.bonentreeToDelete !== null) {
      this.bonEntreeService.deleteBonEntree(this.bonentreeToDelete).subscribe(() => {
        this.bonEntrees = this.bonEntrees.filter(b => b.id !== this.bonentreeToDelete);
        this.filteredBonEntrees = this.filteredBonEntrees.filter(b => b.id !== this.bonentreeToDelete);
        this.modalRef?.close();
      });
    }
  }

  openDetailsModal(content: any, bonEntree: BonEntree): void {
    this.selectedBonEntree = bonEntree;
    this.modalService.open(content, { size: 'lg' });
  }

  showValidateConfirmation(content: any, id: number): void {
    this.bonentreeToValidate = id;
    this.modalRef = this.modalService.open(content);
  }

  confirmValidate(): void {
    if (this.bonentreeToValidate !== null) {
      this.bonEntreeService.validateBonEntree(this.bonentreeToValidate).subscribe(() => {
        this.loadBonEntrees();
        this.modalRef?.close();
      });
    }
  }
}
