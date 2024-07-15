import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Entrepot } from '../models/entrepot';

@Injectable({
  providedIn: 'root'
})
export class EntrepotService {
  private apiUrl = 'http://localhost:8080/api/entrepots';

  constructor(private http: HttpClient) { }

  getEntrepots(): Observable<Entrepot[]> {
    return this.http.get<Entrepot[]>(this.apiUrl);
  }

  getEntrepotById(id: number): Observable<Entrepot> {
    return this.http.get<Entrepot>(`${this.apiUrl}/${id}`);
  }

  createEntrepot(entrepot: Entrepot): Observable<Entrepot> {
    return this.http.post<Entrepot>(this.apiUrl, entrepot);
  }

  updateEntrepot(id: number, entrepot: Entrepot): Observable<Entrepot> {
    return this.http.put<Entrepot>(`${this.apiUrl}/${id}`, entrepot);
  }

  deleteEntrepot(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }
}
