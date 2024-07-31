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
togglePasswordVisibility() {
throw new Error('Method not implemented.');
}


  email: string = '';
  password: string = '';
  isConnected: boolean = false;
  errorMessage: string = '';
  passwordVisible: boolean = false;




  constructor(private authService: AuthService, private router: Router) { }


  ngOnInit(): void {
    // Vérifier si le code s'exécute dans un navigateur avant d'accéder à localStorage
    if (this.authService.isBrowser()) {
      const currentUser = localStorage.getItem('currentUser');
      if (currentUser) {
        this.authService.currentUserSubject.next(JSON.parse(currentUser));
      }
    }
  }


  login(): void {
    this.authService.login(this.email, this.password).subscribe(
      user => {
        this.router.navigate(['/dashboard']);
      },
      error => {
        console.error('Login failed', error);
      }
    );
  }
}
