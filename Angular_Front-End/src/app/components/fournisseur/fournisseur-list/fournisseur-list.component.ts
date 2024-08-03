import {Component, NO_ERRORS_SCHEMA, OnInit} from '@angular/core';
import { CommonModule } from '@angular/common';
import { FournisseurService } from '../../../services/fournisseur.service';
import { Fournisseur } from '../../../models/fournisseur';
import {HttpClient} from "@angular/common/http";
import {Router} from "@angular/router";
import {NgbModal, NgbModalRef} from "@ng-bootstrap/ng-bootstrap";
import {NgxPaginationModule} from "ngx-pagination";
import { AuthService } from '../../../services/auth.service';

@Component({
  selector: 'app-fournisseur-list',
  standalone: true,
  imports: [CommonModule, NgxPaginationModule],
  templateUrl: './fournisseur-list.component.html',
  styleUrl: './fournisseur-list.component.css'
})
export class FournisseurListComponent implements OnInit{

  fournisseurs: Fournisseur[] = [];
  filteredFournisseurs: Fournisseur[] = [];
  page: number = 1;
  itemsPerPage: number = 6;

  fournisseurToDelete: number | null = null;
  fournisseurToEdit: number | null = null;
  private modalRef: NgbModalRef | null = null;
  infoMessage: string = '';
  errorMessage: string = '';

  constructor(private fournisseurService: FournisseurService, private router: Router, private authService: AuthService, private modalService: NgbModal) { }

  ngOnInit(): void {
    const currentUser = this.authService.currentUserValue;
    if (!currentUser || !currentUser.email) {
      this.errorMessage = 'Erreur: email utilisateur non trouvé';
      return;
    }
    const email = currentUser.email;
  
    this.fournisseurService.getFournisseursForCurrentUser(email).subscribe(fournisseurs => {
      if (fournisseurs.length === 0) {
        this.infoMessage = 'Aucune Fournisseurs trouvée pour cet Entrepot.';
        setTimeout(() => this.infoMessage = '', 2000);
      } else {
        this.fournisseurs = fournisseurs;
      }
    }, error => {
        console.error('Erreur lors de la récupération des fournisseurs:', error);
        this.errorMessage = 'Erreur lors de la récupération des fournisseurs.';
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
