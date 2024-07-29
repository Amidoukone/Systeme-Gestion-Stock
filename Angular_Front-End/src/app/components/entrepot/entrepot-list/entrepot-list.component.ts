import {Component, NO_ERRORS_SCHEMA, OnInit} from '@angular/core';
import { CommonModule } from '@angular/common';
import { EntrepotService } from '../../../services/entrepot.service';
import { Entrepot } from '../../../models/entrepot';
import { Router } from '@angular/router';
import {NgbModal, NgbModalRef} from "@ng-bootstrap/ng-bootstrap";

@Component({
  selector: 'app-entrepot-list',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './entrepot-list.component.html',
  styleUrl: './entrepot-list.component.css'
})
export class EntrepotListComponent implements OnInit {
  entrepots: Entrepot[] = [];

  entrepotsToDelete: number | null = null;
  entrepotsToEdit: number | null = null;
  private modalRef: NgbModalRef | null = null;

  constructor(private entrepotService: EntrepotService, private router: Router,  private modalService: NgbModal) { }

  ngOnInit(): void {
    this.loadEntrepots();
  }

  loadEntrepots() {
    this.entrepotService.getEntrepots().subscribe(data => {
      this.entrepots = data;
    });
  }

  addEntrepot(): void {
    this.router.navigate(['/add-entrepot']);
  }

  editEntrepot(id: number): void {
    this.router.navigate(['/edit-entrepot', id]);
  }

  deleteEntrepot(id: number): void {
    this.entrepotService.deleteEntrepot(id).subscribe(() => {
      this.entrepots = this.entrepots.filter(e => e.id !== id);
    });
  }

  showDeleteConfirmation(content: any, id: number): void {
    this.entrepotsToDelete = id;
    this.modalRef = this.modalService.open(content);
  }

  confirmDelete(): void {
    if (this.entrepotsToDelete!== null) {
      this.entrepotService.deleteEntrepot(this.entrepotsToDelete).subscribe(() => {
        this.entrepots = this.entrepots.filter(f => f.id !== this.entrepotsToDelete);
        this.entrepotsToDelete = null;
        this.modalRef?.close();
      });
    }
  }

}
