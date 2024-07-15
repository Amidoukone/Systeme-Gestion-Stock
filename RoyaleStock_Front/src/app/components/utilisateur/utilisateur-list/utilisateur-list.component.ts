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
  roles: Role[] = [];
  entrepots: Entrepot[] = [];

  constructor(
    private utilisateurService: UtilisateurService,
    private roleService: RoleService,
    private entrepotService: EntrepotService,
    private router: Router
  ) { }

  ngOnInit(): void {
    this.roleService.getRoles().subscribe(roleData => {
      this.roles = roleData;
      this.entrepotService.getEntrepots().subscribe(entrepotData => {
        this.entrepots = entrepotData;
        this.utilisateurService.getUtilisateurs().subscribe(utilisateurData => {
          this.utilisateurs = utilisateurData.map(utilisateur => {
            utilisateur.roleName = this.getRoleName(utilisateur.roles_id);
            utilisateur.entrepotName = this.getEntrepotName(utilisateur.entrepots_id);
            return utilisateur;
          });
        });
      });
    });
  }

  getRoleName(roleId: number): string {
    const role = this.roles.find(r => r.roles_id === roleId);
    return role ? role.name : 'Unknown';
  }

  getEntrepotName(entrepotId: number): string {
    const entrepot = this.entrepots.find(e => e.entrepots_id === entrepotId);
    return entrepot ? entrepot.entrepot_name : 'Unknown';
  }

  deleteUtilisateur(id: number): void {
    this.utilisateurService.deleteUtilisateur(id).subscribe(() => {
      this.utilisateurs = this.utilisateurs.filter(u => u.utilisateur_id !== id);
    });
  }

  editUtilisateur(id: number): void {
    this.router.navigate(['/utilisateurs/edit', id]);
  }

  addUtilisateur(): void {
    this.router.navigate(['/utilisateurs/add']);
  }
}
