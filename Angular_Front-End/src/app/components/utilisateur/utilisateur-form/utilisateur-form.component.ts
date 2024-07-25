import { Component, OnInit, NO_ERRORS_SCHEMA } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, Router } from '@angular/router';
import { UtilisateurService } from '../../../services/utilisateur.service';
import { RoleService } from '../../../services/role.service';
import { EntrepotService } from '../../../services/entrepot.service';
import { Utilisateur } from '../../../models/utilisateur';
import { Role } from '../../../models/role';
import { Entrepot } from '../../../models/entrepot';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-utilisateur-form',
  standalone: true,
  imports: [
    CommonModule,
    FormsModule,
  ],
  templateUrl: './utilisateur-form.component.html',
  styleUrls: ['./utilisateur-form.component.scss'],
  schemas: [NO_ERRORS_SCHEMA]
})
export class UtilisateurFormComponent implements OnInit {

  roles: Role[] = [];
  entrepots: Entrepot[] = [];
  utilisateur: Utilisateur = {} as Utilisateur;
  isEditMode: boolean = false;
  selectedRole: Role | null = null;
  selectedEntrepot: Entrepot | null = null;

  constructor(
    private utilisateurService: UtilisateurService,
    private roleService: RoleService,
    private entrepotService: EntrepotService,
    private route: ActivatedRoute,
    private router: Router
  ) { }

  ngOnInit(): void {
    this.loadRolesAndEntrepots();

    const id = this.route.snapshot.paramMap.get('id');
    if (id) {
      this.isEditMode = true;
      this.loadUtilisateurById(+id);
    }
  }

  async loadRolesAndEntrepots() {
    try {
      const [roles, entrepots] = await Promise.all([
        this.roleService.getRoles().toPromise(),
        this.entrepotService.getEntrepots().toPromise()
      ]);

      this.roles = roles || [];
      this.entrepots = entrepots || [];

      console.log('Roles loaded:', this.roles);
      console.log('Entrepots loaded:', this.entrepots);
    } catch (error) {
      console.error('Error loading roles and entrepots:', error);
    }
  }

  async loadUtilisateurById(id: number) {
    try {
      const utilisateur = await this.utilisateurService.getUtilisateurById(id).toPromise();

      if (utilisateur) {
        this.utilisateur = utilisateur;
        this.selectedRole = utilisateur.role || null;
        this.selectedEntrepot = utilisateur.entrepot || null;
        console.log('Utilisateur loaded:', this.utilisateur);
      }
    } catch (error) {
      console.error('Error loading utilisateur:', error);
    }
  }

  onRoleChange(event: any): void {
    this.selectedRole = this.roles.find(role => role.id === +event.target.value) ?? null;
  }

  onEntrepotChange(event: any): void {
    this.selectedEntrepot = this.entrepots.find(entrepot => entrepot.id === +event.target.value) ?? null;
  }

  async onSubmit(event: Event): Promise<void> {
    event.preventDefault();

    if (this.selectedRole && this.selectedEntrepot) {
      this.utilisateur.role = this.selectedRole;
      this.utilisateur.entrepot = this.selectedEntrepot;

      try {
        if (this.isEditMode) {
          await this.utilisateurService.updateUtilisateur(this.utilisateur.id, this.utilisateur).toPromise();
        } else {
          await this.utilisateurService.createUtilisateur(this.utilisateur).toPromise();
        }
        this.router.navigate(['/utilisateurs']);
      } catch (error) {
        console.error('Error saving utilisateur:', error);
      }
    } else {
      console.error('Role and Entrepot must be selected');
    }
  }

}
