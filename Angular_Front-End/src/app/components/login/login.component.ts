import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from '../../services/auth.service';


@Component({
  selector: 'app-login',
  standalone:true,
  imports: [CommonModule, FormsModule],
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})

export class LoginComponent implements OnInit {

  email: string = '';
  password: string = '';
  isConnected: boolean = false;
  errorMessage: string = '';
  passwordVisible: boolean = false;




  constructor(private authService: AuthService, private router: Router) { }


  ngOnInit(): void { 
    if (this.authService.isBrowser()) {
      const currentUser = localStorage.getItem('currentUser');
      if (currentUser) {
        this.authService.currentUserSubject.next(JSON.parse(currentUser));
      }
    }
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

  togglePasswordVisibility() {
    this.passwordVisible = !this.passwordVisible;
  }
}
