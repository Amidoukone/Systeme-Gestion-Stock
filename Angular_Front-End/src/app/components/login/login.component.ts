import {Component, OnInit} from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from '../../services/auth.service';
import {CommonModule} from "@angular/common";
import {FormsModule} from "@angular/forms";

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent implements  OnInit{

  email: string = '';
  password: string = '';
  isConnected: boolean = false;
  errorMessage: string = '';
  passwordVisible: boolean = false;

  constructor(public authService: AuthService, private router: Router) {}

  ngOnInit() {
    // Vérifie si l'objet localStorage est disponible (environnement navigateur)
    if (typeof window !== 'undefined' && window.localStorage) {
      this.isConnected = !!localStorage.getItem("currentUser");
    } else {
      console.warn('localStorage is not available.');
      this.isConnected = false;
    }
  }

  togglePasswordVisibility() {
    this.passwordVisible = !this.passwordVisible;
  }

  login() {
    this.authService.login(this.email, this.password).subscribe(
      response => {
        console.log('Login successful', response);
        // Stocke les informations de l'utilisateur connecté dans le localStorage si disponible
        if (typeof window !== 'undefined' && window.localStorage) {
          localStorage.setItem("currentUser", JSON.stringify(response));
        }
        this.router.navigate(['/dashboard']);
      },
      error => {
        console.error('Connexion échouée🥱', error);
        this.errorMessage = 'Connexion échouée🥱: ' + (error.error?.message || 'Email ou mot de passe invalide');
        setTimeout(() => {
          this.errorMessage = '';
        }, 2000);
        this.email = '';
        this.password = '';
      }
    );
  }

}
