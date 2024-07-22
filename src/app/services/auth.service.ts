import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  
  private authToken: string | null = null;

  constructor(private http: HttpClient) { }

  login(credentials: { email: string; password: string }): Observable<string> {
    return this.http.post<string>('http://localhost:8080/auth/connexion', credentials, { responseType: 'text' as 'json' });
  }

  setAuthToken(token: string) {
    this.authToken = token;
    localStorage.setItem('authToken', token);
  }

  getAuthToken(): string | null {
    return this.authToken || localStorage.getItem('authToken');
  }

  isAuthenticated(): boolean {
    return this.getAuthToken() !== null;
  }

  logout() {
    this.authToken = null;
    localStorage.removeItem('authToken');
  }
}
