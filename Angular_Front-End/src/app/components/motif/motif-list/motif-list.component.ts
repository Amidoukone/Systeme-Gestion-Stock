import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { MotifService } from '../../../services/motif.service';
import { Motif } from '../../../models/motif';
import { CommonModule } from '@angular/common';
import {NgbModal, NgbModalRef} from "@ng-bootstrap/ng-bootstrap";
import { AuthService } from '../../../services/auth.service';

@Component({
  selector: 'app-motif-list',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './motif-list.component.html',
  styleUrls: ['./motif-list.component.css']
})
export class MotifListComponent implements OnInit {
  motifs: Motif[] = [];

  motifToDelete: number | null = null;
  motifToEdit: number | null = null;
  private modalRef: NgbModalRef | null = null;
  infoMessage: string = '';
  errorMessage: string = '';

  constructor(private motifService: MotifService, private router: Router, private authService: AuthService, private modalService: NgbModal) { }

  ngOnInit(): void {
    const currentUser = this.authService.currentUserValue;
    if (!currentUser || !currentUser.email) {
      this.errorMessage = 'Erreur: email utilisateur non trouvé';
      return;
    }
    const email = currentUser.email;
  
    this.motifService.getMotifsForCurrentUser(email).subscribe(motifs => {
      if (motifs.length === 0) {
        this.infoMessage = 'Aucune motif trouvée pour cet Entrepot.';
        setTimeout(() => this.infoMessage = '', 2000);
      } else {
        this.motifs = motifs;
      }
    }, error => {
        console.error('Erreur lors de la récupération des catégories:', error);
        this.errorMessage = 'Erreur lors de la récupération des catégories.';
      });
    
  }


  addMotif(): void {
    this.router.navigate(['/add-motif']);
  }

  editMotif(id: number): void {
    this.router.navigate(['/edit-motif', id]);
  }

  deleteMotif(id: number): void {
    this.motifService.deleteMotif(id).subscribe(() => {
      this.motifs = this.motifs.filter(m => m.id !== id);
    });
  }

  showDeleteConfirmation(content: any, id: number): void {
    this.motifToDelete = id;
    this.modalRef = this.modalService.open(content);
  }

  confirmDelete(): void {
    if (this.motifToDelete !== null) {
      this.motifService.deleteMotif(this.motifToDelete).subscribe(() => {
        this.motifs = this.motifs.filter(f => f.id !== this.motifToDelete);
        this.motifToDelete = null;
        this.modalRef?.close();
      });
    }
  }

}
