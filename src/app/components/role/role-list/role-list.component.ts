import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { RoleService } from '../../../services/role.service';
import { Role } from '../../../models/role';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-role-list',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './role-list.component.html',
  styleUrl: './role-list.component.css'
})
export class RoleListComponent implements OnInit {
  roles: Role[] = [];

  constructor(private roleService: RoleService, private router: Router) { }

  ngOnInit(): void {
    this.loadRoles();
  }

  loadRoles(): void {
    this.roleService.getRoles().subscribe(data => {
      this.roles = data;
      console.log('Roles loaded:', this.roles);
    });
  }

  addRole(): void {
    this.router.navigate(['/add-role']);
  }

  editRole(id: number): void {
    this.router.navigate(['/edit-role', id]);
  }

  deleteRole(id: number): void {
    this.roleService.deleteRole(id).subscribe(() => {
      this.roles = this.roles.filter(r => r.id !== id);
    });
  }
}
