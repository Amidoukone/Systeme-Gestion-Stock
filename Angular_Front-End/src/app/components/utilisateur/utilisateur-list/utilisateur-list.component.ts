import {Component, NO_ERRORS_SCHEMA, OnInit} from '@angular/core';
import { CommonModule } from '@angular/common';
import { UtilisateurService } from '../../../services/utilisateur.service';
import { Utilisateur } from '../../../models/utilisateur'; 
import {Router} from "@angular/router";  
import {NgbModal, NgbModalRef} from "@ng-bootstrap/ng-bootstrap";
import { AuthService } from '../../../services/auth.service';
import {NgxPaginationModule} from "ngx-pagination";
import { CurrentUser } from '../../../models/currentUser';

@Component({
  selector: 'app-utilisateur-list',
  standalone: true,
  imports: [CommonModule, NgxPaginationModule],
  templateUrl: './utilisateur-list.component.html',
  styleUrl: './utilisateur-list.component.css',
  schemas: [ NO_ERRORS_SCHEMA ]
})
export class UtilisateurListComponent implements OnInit {
  utilisateurs: Utilisateur[] = [];
  filteredUtilisateurs: Utilisateur[] = [];
  page: number = 1;
  itemsPerPage: number = 6;

  utilisateurToDelete: number | null = null;
  utilisateurToEdit: number | null = null;
  private modalRef: NgbModalRef | null = null;
  errorMessage = '';
  currentUser: CurrentUser | null = null;

  constructor(private utilisateurService: UtilisateurService, private router: Router, private authService: AuthService, private modalService: NgbModal) { }

  ngOnInit(): void {
    const currentUser = this.authService.currentUserValue;

    if (!currentUser || !currentUser.email) {
      this.errorMessage = 'Erreur: email utilisateur non trouvé';
      return;
    }
    const email = currentUser.email;

    if(currentUser.role === 'ADMIN'){
      this.utilisateurService.getUtilisateurs().subscribe(
        (data) => {
          this.utilisateurs = data;
          if (data.length === 0) {
            this.errorMessage = 'Aucun utilisateur trouvé pour cet entrepôt.';
          }
        },
        (error) => {
          console.error('Error fetching utilisateurs:', error);
          this.errorMessage = 'Erreur lors de la récupération des utilisateurs.';
        }
      );
    }else{
      this.utilisateurService.getUtilisateursByUserOrEntrepot(email).subscribe(
        (data) => {
          this.utilisateurs = data;
          if (data.length === 0) {
            this.errorMessage = 'Aucun utilisateur trouvé pour cet entrepôt.';
          }
        },
        (error) => {
          console.error('Error fetching utilisateurs:', error);
          this.errorMessage = 'Erreur lors de la récupération des utilisateurs.';
        }
      );
    }
  }

  addUtilisateur(): void {
    this.router.navigate(['/add-utilisateur']);
  }

  editUtilisateur(id: number): void {
    this.router.navigate(['/edit-utilisateur', id]);
  }

  deleteUtilisateur(id: number): void {
    this.utilisateurService.deleteUtilisateur(id).subscribe(() => {
      this.utilisateurs = this.utilisateurs.filter(u => u.id !== id);
      this.filteredUtilisateurs = this.filteredUtilisateurs.filter(u => u.id !== id);
    });
  }

  applyFilter(event: Event): void {
    const filterValue = (event.target as HTMLInputElement).value.trim().toLowerCase();
    this.filteredUtilisateurs = this.utilisateurs.filter(utilisateur =>
      utilisateur.username.toLowerCase().includes(filterValue)
    );
  }

  showEditConfirmation(content: any, id: number): void {
    this.utilisateurToEdit = id;
    this.modalRef = this.modalService.open(content);
  }

  confirmEdit(): void {
    if (this.utilisateurToEdit !== null) {
      this.router.navigate(['/edit-utilisateur', this.utilisateurToEdit]);
      this.utilisateurToEdit = null;
      this.modalRef?.close();
    }
  }

  showDeleteConfirmation(content: any, id: number): void {
    this.utilisateurToDelete = id;
    this.modalRef = this.modalService.open(content);
  }

  confirmDelete(): void {
    if (this.utilisateurToDelete !== null) {
      this.utilisateurService.deleteUtilisateur(this.utilisateurToDelete).subscribe(() => {
        this.utilisateurs = this.utilisateurs.filter(f => f.id !== this.utilisateurToDelete);
        this.filteredUtilisateurs = this.filteredUtilisateurs.filter(f => f.id !== this.utilisateurToDelete);
        this.utilisateurToDelete= null;
        this.modalRef?.close();
      });
    }
  }

  hasRole(role: string): boolean {
    return this.authService.hasRole(role);
  }

}
