import { Component, OnInit, NO_ERRORS_SCHEMA } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, Router } from '@angular/router';
import { UtilisateurService } from '../../../services/utilisateur.service'; 
import { EntrepotService } from '../../../services/entrepot.service';
import { Utilisateur } from '../../../models/utilisateur';
import { Role } from '../../../models/role';
import { Entrepot } from '../../../models/entrepot';
import { FormsModule } from '@angular/forms';
import { AuthService } from '../../../services/auth.service';
import { CurrentUser } from '../../../models/CurrentUser';

@Component({
  selector: 'app-utilisateur-form',
  standalone: true,
  imports: [
    CommonModule,
    FormsModule,
  ],
  templateUrl: './utilisateur-form.component.html',
  styleUrls: ['./utilisateur-form.component.scss'],
  schemas: [NO_ERRORS_SCHEMA]
})
export class UtilisateurFormComponent implements OnInit {

  // roles: Role[] = [];
  // entrepots: Entrepot[] = [];
  // utilisateur: Utilisateur = {} as Utilisateur;
  // isEditMode: boolean = false; 
  // selectedEntrepot: Entrepot | null = null;
  // successMessage: string = '';
  // errorMessage: string = '';

  // constructor(
  //   private utilisateurService: UtilisateurService, 
  //   private entrepotService: EntrepotService,
  //   private route: ActivatedRoute,
  //   private router: Router
  // ) { }

  // ngOnInit(): void {
  //   this.loadRolesAndEntrepots();

  //   const id = this.route.snapshot.paramMap.get('id');
  //   if (id) {
  //     this.isEditMode = true;
  //     this.loadUtilisateurById(+id);
  //   }
  // }

  // async loadRolesAndEntrepots() {
  //   try {
  //     const [entrepots] = await Promise.all([ 
  //       this.entrepotService.getEntrepots().toPromise()
  //     ]);

  //     this.entrepots = entrepots || [];

  //     console.log('Roles loaded:', this.roles);
  //     console.log('Entrepots loaded:', this.entrepots);
  //   } catch (error) {
  //     console.error('Error loading roles and entrepots:', error);
  //     this.errorMessage = 'Erreur de chargement des rôles et des entrepôts.';
  //   }
  // }

  // async loadUtilisateurById(id: number) {
  //   try {
  //     const utilisateur = await this.utilisateurService.getUtilisateurById(id).toPromise();

  //     if (utilisateur) {
  //       this.utilisateur = utilisateur; 
  //       this.selectedEntrepot = utilisateur.entrepot || null;
  //       console.log('Utilisateur loaded:', this.utilisateur);
  //     }
  //   } catch (error) {
  //     console.error('Error loading utilisateur:', error);
  //     this.errorMessage = 'Erreur de chargement de l\'utilisateur.';
  //   }
  // } 

  // onEntrepotChange(event: any): void {
  //   this.selectedEntrepot = this.entrepots.find(entrepot => entrepot.id === +event.target.value) ?? null;
  // }

  // async onSubmit(event: Event): Promise<void> {
  //   event.preventDefault();

  //   if (this.selectedEntrepot) { 
  //     this.utilisateur.entrepot = this.selectedEntrepot;

  //     try {
  //       if (this.isEditMode) {
  //         await this.utilisateurService.updateUtilisateur(this.utilisateur.id, this.utilisateur).toPromise();
  //       } else {
  //         await this.utilisateurService.createManager(this.utilisateur).toPromise();
  //       }
  //       this.successMessage = 'Utilisateur enregistré avec succès!';
  //       setTimeout(() => {
  //         this.router.navigate(['/utilisateurs']);
  //       }, 2000);
  //     } catch (error) {
  //       console.error('Error saving utilisateur:', error);
  //       this.errorMessage = 'Erreur lors de l\'enregistrement de l\'utilisateur.';
  //     }
  //   } else {
  //     console.error('Role and Entrepot must be selected');
  //     this.errorMessage = 'L\'entrepôt doit être sélectionnés.';
  //   }
  // }
  
  entrepots: Entrepot[] = [];
  utilisateur: Utilisateur = {} as Utilisateur;
  isEditMode: boolean = false; 
  selectedEntrepot: Entrepot | null = null;
  successMessage: string = '';
  errorMessage: string = '';
  currentUser: CurrentUser | null = null;

  constructor(
    private utilisateurService: UtilisateurService,
    private entrepotService: EntrepotService,
    private route: ActivatedRoute,
    private router: Router,
    private authService: AuthService
  ) { }

  ngOnInit(): void {
    this.loadRolesAndEntrepots();
    this.loadCurrentUser();

    const id = this.route.snapshot.paramMap.get('id');
    if (id) {
      this.isEditMode = true;
      this.loadUtilisateurById(+id);
    }
  }

  async loadRolesAndEntrepots() {
    try {
      const [entrepots] = await Promise.all([
        this.entrepotService.getEntrepots().toPromise()
      ]);

      this.entrepots = entrepots || [];

      console.log('Entrepots loaded:', this.entrepots);
    } catch (error) {
      console.error('Error loading entrepots:', error);
      this.errorMessage = 'Erreur de chargement des entrepôts.';
    }
  }

  async loadUtilisateurById(id: number) {
    try {
      const utilisateur = await this.utilisateurService.getUtilisateurById(id).toPromise();

      if (utilisateur) {
        this.utilisateur = utilisateur; 
        this.selectedEntrepot = utilisateur.entrepot || null;
        console.log('Utilisateur loaded:', this.utilisateur);
      }
    } catch (error) {
      console.error('Error loading utilisateur:', error);
      this.errorMessage = 'Erreur de chargement de l\'utilisateur.';
    }
  }

  loadCurrentUser() {
    this.authService.currentUser.subscribe(
      (user: CurrentUser | null) => {
        this.currentUser = user;
        if (this.currentUser?.role === 'MANAGER') {
          this.selectedEntrepot = this.currentUser.entrepot || null;
        }

      },
      (error) => {
        console.error('Error loading current user:', error);
        this.errorMessage = 'Erreur de chargement de l\'utilisateur courant.';
      }
    );
  }

  onEntrepotChange(event: any): void {
    this.selectedEntrepot = this.entrepots.find(entrepot => entrepot.id === +event.target.value) ?? null;
  }

  async onSubmit(event: Event): Promise<void> {
    event.preventDefault();

    if (this.selectedEntrepot && this.currentUser) {
      this.utilisateur.entrepot = this.selectedEntrepot;

      try {
        if (this.isEditMode) {
          await this.utilisateurService.updateUtilisateur(this.utilisateur.id, this.utilisateur).toPromise();
        } else {
          switch (this.currentUser.role) {
            case 'ADMIN':
              await this.utilisateurService.createManager(this.utilisateur).toPromise();
              break;
            case 'MANAGER':
              await this.utilisateurService.createVendeur(this.utilisateur).toPromise();
              break;
            case 'VENDEUR':
              this.errorMessage = 'Un vendeur ne peut pas créer d\'autres utilisateurs.';
              return;
            default:
              this.errorMessage = 'Rôle de l\'utilisateur courant non pris en charge.';
              return;
          }
        }
        this.successMessage = 'Utilisateur enregistré avec succès!';
        setTimeout(() => {
          this.router.navigate(['/utilisateurs']);
        }, 2000);
      } catch (error) {
        console.error('Error saving utilisateur:', error);
        this.errorMessage = 'Erreur lors de l\'enregistrement de l\'utilisateur.';
      }
    } else {
      console.error('Role and Entrepot must be selected');
      this.errorMessage = 'L\'entrepôt doit être sélectionnés.';
    }
  }
}
