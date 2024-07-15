import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { BonEntree } from '../models/bon-entree';

@Injectable({
  providedIn: 'root'
})
export class BonEntreeService {
  private apiUrl = 'http://localhost:8080/api/bon-entrees';

  constructor(private http: HttpClient) { }

  getBonEntrees(): Observable<BonEntree[]> {
    return this.http.get<BonEntree[]>(this.apiUrl);
  }

  getBonEntreeById(id: number): Observable<BonEntree> {
    return this.http.get<BonEntree>(`${this.apiUrl}/${id}`);
  }

  createBonEntree(bonEntree: BonEntree): Observable<BonEntree> {
    return this.http.post<BonEntree>(this.apiUrl, bonEntree);
  }

  updateBonEntree(id: number, bonEntree: BonEntree): Observable<BonEntree> {
    return this.http.put<BonEntree>(`${this.apiUrl}/${id}`, bonEntree);
  }

  deleteBonEntree(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }
}
