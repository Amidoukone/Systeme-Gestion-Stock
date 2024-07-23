import {Component, Input} from '@angular/core';
import {Router, RouterOutlet} from '@angular/router';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-sidebar',
  standalone: true,
  imports: [RouterOutlet, CommonModule],
  templateUrl: './sidebar.component.html',
  styleUrl: './sidebar.component.scss'
})
export class SidebarComponent {
  @Input() isExpanded = true;

  toggleSidebar() {
    this.isExpanded = !this.isExpanded;
  }

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

  constructor(private router: Router) { }

  navigate(route: string) {
    this.router.navigate([route]);
  }
}
