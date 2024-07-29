import { Component, OnInit } from '@angular/core';
import { UtilisateurService } from '../../services/utilisateur.service';
import { Utilisateur } from '../../models/utilisateur';
import { AuthService } from '../../services/auth.service';
import { Router, ActivatedRoute } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-user-profile',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './user-profile.component.html',
  styleUrls: ['./user-profile.component.css']
})
export class UserProfileComponent implements OnInit {
  utilisateur: any = {}; // Define the utilisateur object
  newPassword: string = '';
  confirmPassword: string = '';
  errorMessage: string = '';

  constructor(private UtilisateurService: UtilisateurService, private authService: AuthService) {}

  ngOnInit(): void {
    this.loadUserProfile();
  }

  loadUserProfile(): void {
    const currentUser = this.authService.currentUserValue;
    if (currentUser && currentUser.id) {
      this.UtilisateurService.getUtilisateurById(currentUser.id).subscribe(
        (data: any) => {
          this.utilisateur = data;
        },
        (error) => {
          console.error('Error fetching user profile', error);
        }
      );
    }
  }

  updateProfile(): void {
    if (this.newPassword && this.newPassword !== this.confirmPassword) {
      this.errorMessage = 'Les mots de passe ne correspondent pas.';
      return;
    }

    const updateData = {
      ...this.utilisateur,
      password: this.newPassword ? this.newPassword : undefined
    };

    this.UtilisateurService.updateUtilisateur(this.utilisateur.id, this.utilisateur).subscribe(
      (data) => {
        console.log('User profile updated successfully', data);
      },
      (error) => {
        console.error('Error updating user profile', error);
      }
    );
  }
}
