import {Component, Input, OnInit} from '@angular/core';
import {Router, RouterOutlet} from '@angular/router';
import { CommonModule } from '@angular/common';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-sidebar',
  standalone: true,
  imports: [RouterOutlet, CommonModule],
  templateUrl: './sidebar.component.html',
  styleUrl: './sidebar.component.scss'
})
export class SidebarComponent implements OnInit{

  @Input() isExpanded = true;
  currentUser: any;

  navItems = [
    { label: 'Dashboard', icon: 'dashboard', route: '/' },
    { label: 'Bons d\'Entrée', icon: 'input', route: '/bon-entree' },
    { label: 'Bons de Sortie', icon: 'output', route: '/bon-sortie' },
    { label: 'Catégories', icon: 'category', route: '/categories' },
    { label: 'Entrepôts', icon: 'store', route: '/entrepots' },
    { label: 'Fournisseurs', icon: 'local_shipping', route: '/fournisseurs' },
    { label: 'Produits', icon: 'inventory', route: '/produits' },
    { label: 'Rôles', icon: 'security', route: '/roles' },
    { label: 'Utilisateurs', icon: 'people', route: '/utilisateurs' }
  ];

  constructor(public authService: AuthService, private router: Router) { }

  ngOnInit() {
    this.currentUser = this.authService.currentUserValue;
  }

  navigate(route: string) {
    this.router.navigate([route]);
  }
  hasRole(role: string): boolean {
    return this.authService.hasRole(role);
  }

  toggleSidebar() {
    this.isExpanded = !this.isExpanded;
  }
}
