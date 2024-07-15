import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';

import { UtilisateurListComponent } from './utilisateur-list/utilisateur-list.component';
import { UtilisateurFormComponent } from './utilisateur-form/utilisateur-form.component';

@NgModule({
  declarations: [
    UtilisateurListComponent,
    UtilisateurFormComponent
  ],
  imports: [
    CommonModule,
    FormsModule,
    MatFormFieldModule,
    MatInputModule,
    MatSelectModule,
    MatButtonModule,
    MatCardModule
  ]
})
export class UtilisateurModule { }
