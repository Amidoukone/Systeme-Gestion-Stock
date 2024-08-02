import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse, HttpHeaders, HttpParams } from '@angular/common/http';
import {Observable, Subscription, throwError} from 'rxjs';
import { Utilisateur } from '../models/utilisateur';
import {observableToBeFn} from "rxjs/internal/testing/TestScheduler";
import { AuthService } from './auth.service';

@Injectable({
  providedIn: 'root'
})
export class UtilisateurService {
  private baseUrl = 'http://localhost:8080/api/utilisateurs'; 

  constructor(private http: HttpClient, private authService: AuthService) { }

  // getUtilisateurs(): Observable<any> {
  //   return this.http.get<any>(`${this.baseUrl}/current`);
  // }
  // getUtilisateurs(): Observable<Utilisateur[]> {
  //   const currentUser = this.authService.currentUserValue;
  //   console.log("email :" ,currentUser.email );
  //   const headers = new HttpHeaders().set('Authorization', `Basic ${btoa(`${currentUser.email}:${currentUser.password}`)}`);
  //   return this.http.get<Utilisateur[]>(`${this.baseUrl}/current`, { headers });
  // }
  // getUtilisateurs(): Observable<Utilisateur[]> {
  //   const token = localStorage.getItem('token'); // Assurez-vous que le token est stocké dans localStorage
  //   const headers = new HttpHeaders().set('Authorization', `Basic ${token}`);
  //   return this.http.get<Utilisateur[]>(`${this.baseUrl}/current`, { headers });
  // }
  // getUtilisateursByUserOrEntrepot(): Observable<Utilisateur[]> {
  //   const token = localStorage.getItem('token');  // Assurez-vous que le token est stocké sous la clé 'token' dans le localStorage
  //   const headers = new HttpHeaders().set('Authorization', `Basic ${token}`);
  //   return this.http.get<Utilisateur[]>(`${this.baseUrl}/current`, { headers });
  // }
  getUtilisateursByUserOrEntrepot(email: string): Observable<Utilisateur[]> { 
    return this.http.get<Utilisateur[]>(`${this.baseUrl}/current/${email}` );
  }

  getUtilisateurById(id: number): Observable<Utilisateur> {
    return this.http.get<Utilisateur>(`${this.baseUrl}/${id}`);
  }

  createAdmin(utilisateur: Utilisateur): Observable<Utilisateur> {
    return this.http.post<Utilisateur>(`${this.baseUrl}/admin`, utilisateur);
  }
  createManager(utilisateur: Utilisateur): Observable<Utilisateur> {
    return this.http.post<Utilisateur>(`${this.baseUrl}/manager`, utilisateur);
  } 
  createVendeur(utilisateur: Utilisateur, managerId: number): Observable<Utilisateur> {
    const payload = { ...utilisateur, managerId };
    return this.http.post<Utilisateur>(`${this.baseUrl}/vendeur`, payload);
  }

  updateUtilisateur(id: number, utilisateur: Utilisateur): Observable<Utilisateur> {
    return this.http.put<Utilisateur>(`${this.baseUrl}/${id}`, utilisateur);
  }

  deleteUtilisateur(id: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/${id}`);
  }

  // private handleError(error: HttpErrorResponse) {
  //   console.error('An error occurred:', error.error);
  //   return throwError(() => new Error(error.error?.message || 'An error occurred'));
  // }
}
