import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { RoleService } from '../../../services/role.service';
import { Role } from '../../../models/role';
import { CommonModule } from '@angular/common';
import {NgbModal, NgbModalRef} from "@ng-bootstrap/ng-bootstrap";

@Component({
  selector: 'app-role-list',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './role-list.component.html',
  styleUrl: './role-list.component.css'
})
export class RoleListComponent implements OnInit {
  roles: Role[] = [];

  roleToDelete: number | null = null;
  roleToEdit: number | null = null;
  private modalRef: NgbModalRef | null = null;

  constructor(private roleService: RoleService, private router: Router,private modalService: NgbModal) { }

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

  showDeleteConfirmation(content: any, id: number): void {
    this.roleToDelete = id;
    this.modalRef = this.modalService.open(content);
  }

  confirmDelete(): void {
    if (this.roleToDelete!== null) {
      this.roleService.deleteRole(this.roleToDelete).subscribe(() => {
        this.roles = this.roles.filter(f => f.id !== this.roleToDelete);
        this.roleToDelete = null;
        this.modalRef?.close();
      });
    }
  }

}
