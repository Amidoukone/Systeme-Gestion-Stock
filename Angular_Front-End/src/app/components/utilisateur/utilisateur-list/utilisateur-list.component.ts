import {Component, NO_ERRORS_SCHEMA, OnInit} from '@angular/core';
import { CommonModule } from '@angular/common';
import { UtilisateurService } from '../../../services/utilisateur.service';
import { Utilisateur } from '../../../models/utilisateur';
import {HttpClient} from "@angular/common/http";
import {Router} from "@angular/router";
import {RoleService} from "../../../services/role.service";
import {EntrepotService} from "../../../services/entrepot.service";
import {Role} from "../../../models/role";
import {Entrepot} from "../../../models/entrepot";

@Component({
  selector: 'app-utilisateur-list',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './utilisateur-list.component.html',
  styleUrl: './utilisateur-list.component.css',
  schemas: [ NO_ERRORS_SCHEMA ]
})
export class UtilisateurListComponent implements OnInit {
  utilisateurs: Utilisateur[] = [];
  filteredUtilisateurs: Utilisateur[] = [];

  constructor(private utilisateurService: UtilisateurService, private router: Router) { }

  ngOnInit(): void {
    this.utilisateurService.getUtilisateurs().subscribe(data => {

      this.utilisateurs = data;
      console.log('Utilisateurs loaded:', data);
      this.filteredUtilisateurs = data;
      console.log('Utilisateurs loaded:', this.utilisateurs);
    });
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
}
