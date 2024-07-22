import { Component, OnInit } from '@angular/core';
import { UtilisateurService } from '../../services/utilisateur.service';
import { Utilisateur } from '../../models/utilisateur';
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
  utilisateur: Utilisateur = {} as Utilisateur;
  newPassword: string = '';
  confirmPassword: string = '';

  constructor(
    private utilisateurService: UtilisateurService,
    private router: Router,
    private route: ActivatedRoute
  ) { }

  ngOnInit(): void {
    //this.loadUserProfile();
  }

 /* loadUserProfile(): void {
    const id = +this.route.snapshot.paramMap.get('id');
    this.utilisateurService.getUtilisateurById(id).subscribe(data => {
      this.utilisateur = data;
    });
  }*/

  updateProfile(): void {
    if (this.newPassword !== this.confirmPassword) {
      alert('Les mots de passe ne correspondent pas.');
      return;
    }

    if (this.newPassword) {
      this.utilisateur.password = this.newPassword;
    }

    this.utilisateurService.updateUtilisateur(this.utilisateur.id, this.utilisateur).subscribe(() => {
      alert('Profil mis à jour avec succès');
      this.router.navigate(['/dashboard']);
    });
  }
}
