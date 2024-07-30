import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import {Observable, Subscription, throwError} from 'rxjs';
import { Utilisateur } from '../models/utilisateur';
import {observableToBeFn} from "rxjs/internal/testing/TestScheduler";

@Injectable({
  providedIn: 'root'
})
export class UtilisateurService {
  private baseUrl = 'http://localhost:8080/api/utilisateurs'; 

  constructor(private http: HttpClient) { }

  getUtilisateurs(): Observable<any> {
    return this.http.get<any>(this.baseUrl);
  }

  // getUtilisateursByEntrepot(): Observable<any> {
  //   return this.http.get<any>(`${this.baseUrl}/entrepots/${id}`);
  // }

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
