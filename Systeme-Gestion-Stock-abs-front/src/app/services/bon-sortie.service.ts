import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { BonSortie } from '../models/bon-sortie';

@Injectable({
  providedIn: 'root'
})
export class BonSortieService {
  private apiUrl = 'http://localhost:3000/bon-sorties';

  constructor(private http: HttpClient) { }

  getBonSorties(): Observable<BonSortie[]> {
    return this.http.get<BonSortie[]>(this.apiUrl);
  }

  getBonSortieById(id: number): Observable<BonSortie> {
    return this.http.get<BonSortie>(`${this.apiUrl}/${id}`);
  }

  createBonSortie(bonSortie: BonSortie): Observable<BonSortie> {
    return this.http.post<BonSortie>(this.apiUrl, bonSortie);
  }

  updateBonSortie(id: number, bonSortie: BonSortie): Observable<BonSortie> {
    return this.http.put<BonSortie>(`${this.apiUrl}/${id}`, bonSortie);
  }

  deleteBonSortie(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }
}
