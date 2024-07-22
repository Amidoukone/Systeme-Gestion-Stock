import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { AppRoutingModule } from './app-routing.module';
import { HttpClientModule, HTTP_INTERCEPTORS  } from '@angular/common/http';
import { HttpErrorInterceptor } from './services/http-interceptor.service';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';

import { AppComponent } from './app.component';
import { DashboardComponent } from './components/dashboard/dashboard.component';
import { NavbarComponent } from './components/navbar/navbar.component';
import { SidebarComponent } from './components/sidebar/sidebar.component';
import { FooterComponent } from './components/footer/footer.component';
import { HeaderComponent } from './components/header/header.component';
import { LoginComponent } from './components/login/login.component';

// Bon Entree
import { BonEntreeListComponent } from './components/bon-entree/bon-entree-list/bon-entree-list.component';
import { BonEntreeFormComponent } from './components/bon-entree/bon-entree-form/bon-entree-form.component';
import { BonEntreePrintComponent } from './components/bon-entree/bon-entree-print/bon-entree-print.component';

// Bon Sortie
import { BonSortieListComponent } from './components/bon-sortie/bon-sortie-list/bon-sortie-list.component';
import { BonSortieFormComponent } from './components/bon-sortie/bon-sortie-form/bon-sortie-form.component';
import { BonSortiePrintComponent } from './components/bon-sortie/bon-sortie-print/bon-sortie-print.component';

// Other Components
import { CategorieListComponent } from './components/categorie/categorie-list/categorie-list.component';
import { CategorieFormComponent } from './components/categorie/categorie-form/categorie-form.component';
import { EntrepotListComponent } from './components/entrepot/entrepot-list/entrepot-list.component';
import { EntrepotFormComponent } from './components/entrepot/entrepot-form/entrepot-form.component';
import { FournisseurListComponent } from './components/fournisseur/fournisseur-list/fournisseur-list.component';
import { FournisseurFormComponent } from './components/fournisseur/fournisseur-form/fournisseur-form.component';
import { ProduitListComponent } from './components/produit/produit-list/produit-list.component';
import { ProduitFormComponent } from './components/produit/produit-form/produit-form.component';
import { RoleListComponent } from './components/role/role-list/role-list.component';
import { RoleFormComponent } from './components/role/role-form/role-form.component';
import { UtilisateurListComponent } from './components/utilisateur/utilisateur-list/utilisateur-list.component';
import { UtilisateurFormComponent } from './components/utilisateur/utilisateur-form/utilisateur-form.component';

import { BonEntreeService } from './services/bon-entree.service';
import { BonSortieService } from './services/bon-sortie.service';
import { CategorieService } from './services/categorie.service';
import { EntrepotService } from './services/entrepot.service';
import { FournisseurService } from './services/fournisseur.service';
import { ProduitService } from './services/produit.service';
import { RoleService } from './services/role.service';
import { UtilisateurService } from './services/utilisateur.service';
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";

@NgModule({
  declarations: [
    AppComponent,
    DashboardComponent,
    NavbarComponent,
    SidebarComponent,
    FooterComponent,
    HeaderComponent,
    LoginComponent,
    BonEntreeListComponent,
    BonEntreeFormComponent,
    BonEntreePrintComponent,
    BonSortieListComponent,
    BonSortieFormComponent,
    BonSortiePrintComponent,
    CategorieListComponent,
    CategorieFormComponent,
    EntrepotListComponent,
    EntrepotFormComponent,
    FournisseurListComponent,
    FournisseurFormComponent,
    ProduitListComponent,
    ProduitFormComponent,
    RoleListComponent,
    RoleFormComponent,
    UtilisateurListComponent,
    UtilisateurFormComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule,
    CommonModule,
    BrowserAnimationsModule,
  ],
  providers: [
    BonEntreeService,
    BonSortieService,
    CategorieService,
    EntrepotService,
    FournisseurService,
    ProduitService,
    RoleService,
    UtilisateurService,
    { provide: HTTP_INTERCEPTORS, useClass: HttpErrorInterceptor, multi: true }
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
