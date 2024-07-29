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


  constructor(public authService: AuthService, private router: Router) {}

  ngOnInit() {
    this.isConnected = !!localStorage.getItem("currentUser");
  }

  login() {
    this.authService.login(this.email, this.password).subscribe(
      response => {
        console.log('Login successful', response);
        this.router.navigate(['/dashboard']);
      },
      error => {
        // console.error('Login failed', error);
        console.error('Connexion échouée🥱', error);
        this.errorMessage = 'Connexion échouée🥱: ' + (error.error?.message || 'Email or Password invalide');
        setTimeout(() => {
          this.errorMessage = '';
        }, 2000);
        this.email = '';
        this.password = '';
        
      }
    );
  } 
}
