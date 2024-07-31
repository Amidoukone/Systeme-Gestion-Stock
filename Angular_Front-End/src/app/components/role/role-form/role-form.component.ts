import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { RoleService } from '../../../services/role.service';
import { Role } from '../../../models/role';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-role-form',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './role-form.component.html',
  styleUrls: ['./role-form.component.css']
})
export class RoleFormComponent implements OnInit {
  role: Role = {} as Role;
  isEditMode: boolean = false;
  successMessage: string = '';
  errorMessage: string = '';

  constructor(
    private roleService: RoleService,
    private route: ActivatedRoute,
    private router: Router
  ) { }

  ngOnInit(): void {
    const id = this.route.snapshot.paramMap.get('id');
    if (id) {
      this.isEditMode = true;
      this.loadRoleById(+id);
    }
  }

  loadRoleById(id: number): void {
    this.roleService.getRoleById(id).subscribe(
      data => {
        this.role = data;
        console.log('Role loaded:', this.role);
      },
      error => {
        console.error('Error loading role:', error);
        this.errorMessage = 'Erreur de chargement du rôle.';
        setTimeout(() => this.errorMessage = '', 3000); // Clear error message after 3 seconds
      }
    );
  }

  onSubmit(): void {
    if (this.isEditMode) {
      this.roleService.updateRole(this.role.id, this.role).subscribe(
        () => {
          this.successMessage = `Rôle "${this.role.name}" mis à jour avec succès!`;
          setTimeout(() => this.successMessage = '', 3000); // Clear success message after 3 seconds
          setTimeout(() => this.router.navigate(['/roles']), 3000); // Navigate after 3 seconds
        },
        error => {
          console.error('Error updating role:', error);
          this.errorMessage = 'Erreur lors de la mise à jour du rôle.';
          setTimeout(() => this.errorMessage = '', 3000); // Clear error message after 3 seconds
        }
      );
    } else {
      this.roleService.createRole(this.role).subscribe(
        () => {
          this.successMessage = `Rôle "${this.role.name}" créé avec succès!`;
          setTimeout(() => this.successMessage = '', 3000); // Clear success message after 3 seconds
          setTimeout(() => this.router.navigate(['/roles']), 3000); // Navigate after 3 seconds
        },
        error => {
          console.error('Error creating role:', error);
          this.errorMessage = 'Erreur lors de la création du rôle.';
          setTimeout(() => this.errorMessage = '', 3000); // Clear error message after 3 seconds
        }
      );
    }
  }
}
