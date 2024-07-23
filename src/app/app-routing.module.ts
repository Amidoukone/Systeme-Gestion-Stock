import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { DashboardComponent } from './components/dashboard/dashboard.component';
import { BonEntreeListComponent } from './components/bon-entree/bon-entree-list/bon-entree-list.component';
import { BonEntreeFormComponent } from './components/bon-entree/bon-entree-form/bon-entree-form.component';
import { BonEntreePrintComponent } from './components/bon-entree/bon-entree-print/bon-entree-print.component';
import { BonSortieListComponent } from './components/bon-sortie/bon-sortie-list/bon-sortie-list.component';
import { BonSortieFormComponent } from './components/bon-sortie/bon-sortie-form/bon-sortie-form.component';
import { BonSortiePrintComponent } from './components/bon-sortie/bon-sortie-print/bon-sortie-print.component';
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
import { LoginComponent } from './components/login/login.component';
import { AuthGuard } from './auth.guard';

const routes: Routes = [
  { path: '', redirectTo: '/login', pathMatch: 'full'},
  { path: 'dashboard', component: DashboardComponent, canActivate: [AuthGuard] },
  { path: 'bon-entree', component: BonEntreeListComponent, canActivate: [AuthGuard] },
  { path: 'add-bon-entree', component: BonEntreeFormComponent, canActivate: [AuthGuard] },
  { path: 'edit-bon-entree/:id', component: BonEntreeFormComponent, canActivate: [AuthGuard] },
  { path: 'print-bon-entree/:id', component: BonEntreePrintComponent, canActivate: [AuthGuard] },
  { path: 'bon-sortie', component: BonSortieListComponent, canActivate: [AuthGuard] },
  { path: 'bon-sortie/add', component: BonSortieFormComponent, canActivate: [AuthGuard] },
  { path: 'bon-sortie/print/:id', component: BonSortiePrintComponent, canActivate: [AuthGuard] },
  { path: 'categories', component: CategorieListComponent, canActivate: [AuthGuard] },
  { path: 'add-categorie', component: CategorieFormComponent, canActivate: [AuthGuard] },
  { path: 'edit-categorie/:id', component: CategorieFormComponent, canActivate: [AuthGuard] },
  { path: 'entrepots', component: EntrepotListComponent, canActivate: [AuthGuard] },
  { path: 'add-entrepot', component: EntrepotFormComponent, canActivate: [AuthGuard] },
  { path: 'edit-entrepot/:id', component: EntrepotFormComponent, canActivate: [AuthGuard] },
  { path: 'fournisseurs', component: FournisseurListComponent, canActivate: [AuthGuard] },
  { path: 'add-fournisseur', component: FournisseurFormComponent, canActivate: [AuthGuard] },
  { path: 'edit-fournisseur/:id', component: FournisseurFormComponent, canActivate: [AuthGuard] },
  { path: 'produits', component: ProduitListComponent, canActivate: [AuthGuard] },
  { path: 'add-produit', component: ProduitFormComponent, canActivate: [AuthGuard] },
  { path: 'edit-produit/:id', component: ProduitFormComponent, canActivate: [AuthGuard] },
  { path: 'roles', component: RoleListComponent, canActivate: [AuthGuard] },
  { path: 'add-role', component: RoleFormComponent, canActivate: [AuthGuard] },
  { path: 'edit-role/:id', component: RoleFormComponent, canActivate: [AuthGuard] },
  { path: 'utilisateurs', component: UtilisateurListComponent, canActivate: [AuthGuard] },
  { path: 'add-utilisateur', component: UtilisateurFormComponent, canActivate: [AuthGuard] },
  { path: 'edit-utilisateur/:id', component: UtilisateurFormComponent, canActivate: [AuthGuard] },
  { path: 'login', component: LoginComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
