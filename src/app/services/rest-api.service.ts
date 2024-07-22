import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class RestApiService {

  constructor(private http:HttpClient) { }

  public login(username:string, password:string) {
    const headers = new HttpHeaders({Authorizatin : 'Basic'+btoa(username+""+password)});
    this.http.get("http://localhost:8080/auth/connexion",{headers, responseType: "text" as "json"});
  }
  
}
