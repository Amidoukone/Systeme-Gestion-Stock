import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { BonEntree } from '../models/bon-entree';

@Injectable({
  providedIn: 'root'
})
export class BonEntreeService {
  private baseUrl = 'http://localhost:8080/api/bon-entrees';

  constructor(private http: HttpClient) { }

  getBonEntrees(): Observable<BonEntree[]> {
    return this.http.get<BonEntree[]>(`${this.baseUrl}`);
  }

  getBonEntreeById(id: number): Observable<BonEntree> {
    return this.http.get<BonEntree>(`${this.baseUrl}/${id}`);
  }

  createBonEntree(bonEntree: {
    utilisateur: { id: number; username: string };
    fournisseur: { id: number; fournName: string };
    id: number;
    dateCommande: Date;
    statut: string;
    detailEntrees: any[];
    date_commande: string
  }): Observable<BonEntree> {
    return this.http.post<BonEntree>(`${this.baseUrl}`, bonEntree);
  }

    updateBonEntree(id: number, bonEntree: {
        utilisateur: { id: number; username: string };
        fournisseur: { id: number; fournName: string };
        id: number;
        dateCommande: Date;
        statut: string;
        detailEntrees: any[];
        date_commande: string
    }): Observable<BonEntree> {
    return this.http.put<BonEntree>(`${this.baseUrl}/${id}`, bonEntree);
  }

  deleteBonEntree(id: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/${id}`);
  }
}
