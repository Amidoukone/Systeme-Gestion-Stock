import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { BonSortieService } from '../../../services/bon-sortie.service';
import { BonSortie } from '../../../models/bon-sortie';
import { RouterModule } from '@angular/router';
import { ActivatedRoute, Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';

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

 bonsortieToDelete: number | null = null;
  bonsortieToEdit: number | null = null;
  private modalRef: NgbModalRef | null = null;

  constructor(
    private bonSortieService: BonSortieService,
    private route: ActivatedRoute,
    private router: Router,
    private modalService: NgbModal


  ) {}

  ngOnInit(): void {
    this.loadBonSorties();
  }

  loadBonSorties(): void {
    this.bonSortieService.getBonSorties().subscribe(data => {
      console.log(data);
      this.bonSorties = data;
      this.filteredBonSorties = data;
    });
  }

  applyFilter(event: Event): void {
    const filterValue = (event.target as HTMLInputElement).value.toLowerCase();
    this.filteredBonSorties = this.bonSorties.filter(bonSortie =>
      bonSortie.motif.title.toLowerCase().includes(filterValue));
  }

  deleteBonSortie(id: number): void {
    this.bonSortieService.deleteBonSortie(id).subscribe(() => {
      this.loadBonSorties();
    });
  }

  printBonSortie(id: number): void {
    this.router.navigate(['/print-bon-sortie', id]);
  }

  navigateToAddDetail(bonSortieId: number): void {
    this.router.navigate(['/bon-sortie-detail', bonSortieId]);
  }



  showEditConfirmation(content: any, id: number): void {
    this.bonsortieToEdit = id;
    this.modalRef = this.modalService.open(content);
  }

  confirmEdit(): void {
    if (this.bonsortieToEdit !== null) {
      this.router.navigate(['/edit-fournisseur', this.bonsortieToEdit]);
      this.bonsortieToEdit = null;
      this.modalRef?.close();
    }
  }

  showDeleteConfirmation(content: any, id: number): void {
    this.bonsortieToDelete= id;
    this.modalRef = this.modalService.open(content);
  }

  confirmDelete(): void {
    if (this.bonsortieToDelete !== null) {
      this.bonSortieService.deleteBonSortie(this.bonsortieToDelete).subscribe(() => {
        this.bonSorties = this.bonSorties.filter(f => f.id !== this.bonsortieToDelete);
        this.filteredBonSorties = this.filteredBonSorties.filter(f => f.id !== this.bonsortieToDelete);
        this.bonsortieToDelete= null;
        this.modalRef?.close();
      });
    }
  }


}
