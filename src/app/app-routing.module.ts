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

const routes: Routes = [
  { path: '', component: DashboardComponent },
  { path: 'bon-entree', component: BonEntreeListComponent },
  { path: 'add-bon-entree', component: BonEntreeFormComponent },
  { path: 'edit-bon-entree/:id', component: BonEntreeFormComponent },
  { path: 'print-bon-entree/:id', component: BonEntreePrintComponent },
  { path: 'bon-sortie', component: BonSortieListComponent },
  { path: 'bon-sortie/add', component: BonSortieFormComponent },
  { path: 'bon-sortie/print/:id', component: BonSortiePrintComponent },
  { path: 'categories', component: CategorieListComponent },
  { path: 'add-categorie', component: CategorieFormComponent },
  { path: 'edit-categorie/:id', component: CategorieFormComponent },
  { path: 'entrepots', component: EntrepotListComponent },
  { path: 'add-entrepot', component: EntrepotFormComponent },
  { path: 'edit-entrepot/:id', component: EntrepotFormComponent },
  { path: 'fournisseurs', component: FournisseurListComponent },
  { path: 'add-fournisseur', component: FournisseurFormComponent },
  { path: 'edit-fournisseur/:id', component: FournisseurFormComponent },
  { path: 'produits', component: ProduitListComponent },
  { path: 'add-produit', component: ProduitFormComponent },
  { path: 'edit-produit/:id', component: ProduitFormComponent },
  { path: 'roles', component: RoleListComponent },
  { path: 'add-role', component: RoleFormComponent },
  { path: 'edit-role/:id', component: RoleFormComponent },
  { path: 'utilisateurs', component: UtilisateurListComponent },
  { path: 'add-utilisateur', component: UtilisateurFormComponent },
  { path: 'edit-utilisateur/:id', component: UtilisateurFormComponent },
  { path: 'login', component: LoginComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
