import {Component, Input, OnInit} from '@angular/core';
import {Router, RouterOutlet} from '@angular/router';
import { CommonModule } from '@angular/common';
import {AuthService} from "../../services/auth.service";

@Component({
  selector: 'app-sidebar',
  standalone: true,
  imports: [RouterOutlet, CommonModule],
  templateUrl: './sidebar.component.html',
  styleUrl: './sidebar.component.css'
})
export class SidebarComponent implements OnInit {

  @Input() isExpanded = true;
  currentUser: any;

  constructor(public authService: AuthService) {}

  
  ngOnInit() {
    this.currentUser = this.authService.currentUserValue;
  }

  hasRole(role: string): boolean {
    return this.authService.hasRole(role);
  }

  toggleSidebar() {
    this.isExpanded = !this.isExpanded;
  }
}
