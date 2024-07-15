import { Component, OnInit, NO_ERRORS_SCHEMA } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, Router } from '@angular/router';
import { UtilisateurService } from '../../../services/utilisateur.service';
import { RoleService } from '../../../services/role.service';
import { EntrepotService } from '../../../services/entrepot.service';
import { Utilisateur } from '../../../models/utilisateur';
import { Role } from '../../../models/role';
import { Entrepot } from '../../../models/entrepot';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatOptionModule } from '@angular/material/core';
import { MatSelectModule } from '@angular/material/select';
import { MatCardModule } from '@angular/material/card';
import { FormsModule } from '@angular/forms';
import { MatInputModule } from '@angular/material/input';
import {MatGridList, MatGridTile} from "@angular/material/grid-list";

@Component({
  selector: 'app-utilisateur-form',
  standalone: true,
  imports: [
    MatFormFieldModule,
    MatSelectModule,
    MatOptionModule,
    CommonModule,
    MatCardModule,
    FormsModule,
    MatInputModule,
    MatGridTile,
    MatGridList
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
  selectedRoleId: number | null = null;
  selectedEntrepotId: number | null = null;

  constructor(
    private utilisateurService: UtilisateurService,
    private roleService: RoleService,
    private entrepotService: EntrepotService,
    private route: ActivatedRoute,
    private router: Router
  ) { }

  ngOnInit(): void {
    this.roleService.getRoles().subscribe(data => {
      this.roles = data;
      console.log('Roles loaded:', this.roles);
    });
    this.entrepotService.getEntrepots().subscribe(data => {
      this.entrepots = data;
      console.log('Entrepots loaded:', this.entrepots);
    });

    const id = this.route.snapshot.paramMap.get('id');
    if (id) {
      this.isEditMode = true;
      this.utilisateurService.getUtilisateurById(+id).subscribe(data => {
        this.utilisateur = data;
        this.selectedRoleId = data.roles_id;
        this.selectedEntrepotId = data.entrepots_id;
        console.log('Utilisateur loaded:', this.utilisateur);
      });
    }
  }

  onRoleChange(event: any): void {
    this.selectedRoleId = event.value;
    console.log('Role selected:', this.selectedRoleId);
  }

  onEntrepotChange(event: any): void {
    this.selectedEntrepotId = event.value;
    console.log('Entrepot selected:', this.selectedEntrepotId);
  }

  onSubmit(event: Event): void {
    event.preventDefault();

    if (this.selectedRoleId !== null && this.selectedEntrepotId !== null) {
      this.utilisateur.roles_id = this.selectedRoleId;
      this.utilisateur.entrepots_id = this.selectedEntrepotId;

      if (this.isEditMode) {
        this.utilisateurService.updateUtilisateur(this.utilisateur.utilisateur_id, this.utilisateur).subscribe(() => {
          this.router.navigate(['/utilisateurs']);
        });
      } else {
        this.utilisateurService.createUtilisateur(this.utilisateur).subscribe(() => {
          this.router.navigate(['/utilisateurs']);
        });
      }
    } else {
      console.error('Role and Entrepot must be selected');
    }
  }
}
