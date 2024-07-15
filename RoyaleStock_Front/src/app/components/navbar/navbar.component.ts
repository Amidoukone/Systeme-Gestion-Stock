import { Component, Output, EventEmitter, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HttpClient } from '@angular/common/http';
import { NotificationService } from '../../services/notification.service';
import { Notification } from '../../models/notification';
import {MatMenu} from "@angular/material/menu";
import {MatIcon} from "@angular/material/icon";
import {MatToolbar} from "@angular/material/toolbar";

@Component({
  selector: 'app-navbar',
  standalone: true,
  imports: [
    MatMenu,
    MatIcon,
    MatToolbar,
    CommonModule
  ],
  templateUrl: './navbar.component.html',
  styleUrl: './navbar.component.scss'
})
export class NavbarComponent implements OnInit {
  @Output() sidebarToggle = new EventEmitter<void>();
  notifications: any[] = [];
  showNotifications = false;

  constructor(private http: HttpClient) { }

  ngOnInit(): void {
    this.http.get<any[]>('http://localhost:8080/api/notifications')
      .subscribe(data => this.notifications = data);
  }

  toggleSidebar(): void {
    this.sidebarToggle.emit();
  }

  toggleNotifications(): void {
    this.showNotifications = !this.showNotifications;
  }
}
