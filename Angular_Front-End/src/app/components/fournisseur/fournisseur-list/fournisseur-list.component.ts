import {Component, NO_ERRORS_SCHEMA, OnInit} from '@angular/core';
import { CommonModule } from '@angular/common';
import { FournisseurService } from '../../../services/fournisseur.service';
import { Fournisseur } from '../../../models/fournisseur';
import {HttpClient} from "@angular/common/http";
import {Router} from "@angular/router";
import {NgbModal, NgbModalRef} from "@ng-bootstrap/ng-bootstrap";

@Component({
  selector: 'app-fournisseur-list',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './fournisseur-list.component.html',
  styleUrl: './fournisseur-list.component.css'
})
export class FournisseurListComponent implements OnInit{

  fournisseurs: Fournisseur[] = [];
  filteredFournisseurs: Fournisseur[] = [];


  fournisseurToDelete: number | null = null;
  fournisseurToEdit: number | null = null;
  private modalRef: NgbModalRef | null = null;

  constructor(private fournisseurService: FournisseurService, private router: Router,private modalService: NgbModal) { }

  ngOnInit(): void {
    this.loadFournisseurs();
  }

  loadFournisseurs(): void {
    this.fournisseurService.getFournisseurs().subscribe(data => {
      this.fournisseurs = data;
      this.filteredFournisseurs = data;
      console.log('Fournisseurs loaded:', this.fournisseurs);
    });
  }

  addFournisseur(): void {
    this.router.navigate(['/add-fournisseur']);
  }

  editFournisseur(id: number): void {
    this.router.navigate(['/edit-fournisseur', id]);
  }

  deleteFournisseur(id: number): void {
    this.fournisseurService.deleteFournisseur(id).subscribe(() => {
      this.fournisseurs = this.fournisseurs.filter(f => f.id !== id);
      this.filteredFournisseurs = this.filteredFournisseurs.filter(f => f.id !== id);
    });
  }

  applyFilter(event: Event): void {
    const filterValue = (event.target as HTMLInputElement).value.trim().toLowerCase();
    this.filteredFournisseurs = this.fournisseurs.filter(fournisseur =>
      fournisseur.fournName.toLowerCase().includes(filterValue)
    );
  }

  showDeleteConfirmation(content: any, id: number): void {
    this.fournisseurToDelete = id;
    this.modalRef = this.modalService.open(content);
  }

  confirmDelete(): void {
    if (this.fournisseurToDelete !== null) {
      this.fournisseurService.deleteFournisseur(this.fournisseurToDelete).subscribe(() => {
        this.fournisseurs = this.fournisseurs.filter(f => f.id !== this.fournisseurToDelete);
        this.filteredFournisseurs = this.filteredFournisseurs.filter(f => f.id !== this.fournisseurToDelete);
        this.fournisseurToDelete = null;
        this.modalRef?.close();
      });
    }
  }



}
