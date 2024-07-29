import { Component, Output, EventEmitter, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HttpClient } from '@angular/common/http';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { NotificationService } from '../../services/notification.service';
import { Notification } from '../../models/notification';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-navbar',
  standalone: true,
  imports: [CommonModule, FormsModule, ReactiveFormsModule, HttpClientModule],
  templateUrl: './navbar.component.html',
  styleUrl: './navbar.component.css'
})
export class NavbarComponent implements OnInit {
  @Output() sidebarToggle = new EventEmitter<void>();
  notifications: Notification[] = [];
  showNotifications: boolean = false;
  showProfile: boolean = false;

  constructor(private notificationService: NotificationService, public authService: AuthService) {}

  ngOnInit(): void {
    this.loadUnreadNotifications();
  }

  loadUnreadNotifications(): void {
    this.notificationService.getUnreadNotifications().subscribe(notifications => {
      this.notifications = notifications;
    });
  }

  toggleNotifications(): void {
    this.showNotifications = !this.showNotifications;
    if (this.showNotifications) {
      this.showProfile = false; // Ferme le menu Profil
    }
  }

  toggleProfile(): void {
    this.showProfile = !this.showProfile;
    if (this.showProfile) {
      this.showNotifications = false; // Ferme le menu Notifications
    }
  }

  markAsRead(notification: Notification): void {
    if (!notification.read) {
      this.notificationService.markAsRead(notification.id).subscribe(() => {
        notification.read = true;
        this.loadUnreadNotifications(); // Rafraîchit les notifications
      });
    }
  }

  getUnreadCount(): number {
    return this.notifications.length;
  }

  toggleSidebar(): void {
    this.sidebarToggle.emit();
  }

  get currentUser() {
    return this.authService.currentUserValue;
  }

  logout(): void {
    this.authService.logout();
  }

  hasRole(role: string): boolean {
    return this.authService.hasRole(role);
  }

  getEntrepotName(): string {
    return this.currentUser && this.currentUser.entrepot ? this.currentUser.entrepot.name : 'RoyalStock';
  }
}
