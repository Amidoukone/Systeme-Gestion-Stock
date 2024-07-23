import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { RoleService } from '../../../services/role.service';
import { Role } from '../../../models/role';
import { FormsModule } from '@angular/forms';
import {CommonModule} from "@angular/common";

@Component({
  selector: 'app-role-form',
  standalone: true,
  imports: [CommonModule,FormsModule],
  templateUrl: './role-form.component.html',
  styleUrl: './role-form.component.css'
})
export class RoleFormComponent implements OnInit {
  role: Role = {} as Role;
  isEditMode: boolean = false;

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
    this.roleService.getRoleById(id).subscribe(data => {
      this.role = data;
      console.log('Role loaded:', this.role);
    });
  }

  onSubmit(): void {
    if (this.isEditMode) {
      this.roleService.updateRole(this.role.id, this.role).subscribe(() => {
        this.router.navigate(['/roles']);
      });
    } else {
      this.roleService.createRole(this.role).subscribe(() => {
        this.router.navigate(['/roles']);
      });
    }
  }
}
