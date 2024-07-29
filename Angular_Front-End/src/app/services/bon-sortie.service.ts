import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { BonSortie } from '../models/bon-sortie';
import {Produit} from "../models/produit";
import {DetailSortie} from "../models/detail-sortie";

@Injectable({
  providedIn: 'root'
})
export class BonSortieService {
  private apiUrl = 'http://localhost:8080/api/bon-sorties';

  constructor(private http: HttpClient) {}

  getBonSorties(): Observable<BonSortie[]> {
    return this.http.get<BonSortie[]>(this.apiUrl);
  }

  getBonSortieById(id: number): Observable<BonSortie> {
    return this.http.get<BonSortie>(`${this.apiUrl}/${id}`);
  }

  createBonSortie(bonSortie: BonSortie): Observable<BonSortie> {
    return this.http.post<BonSortie>(this.apiUrl, bonSortie);
  }

  updateBonSortie(id: number, formattedBonSortie: BonSortie): Observable<BonSortie> {
    return this.http.put<BonSortie>(`${this.apiUrl}/${formattedBonSortie.id}`, formattedBonSortie);
  }

  deleteBonSortie(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }
}
