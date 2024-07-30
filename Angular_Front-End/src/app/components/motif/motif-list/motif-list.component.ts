import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { MotifService } from '../../../services/motif.service';
import { Motif } from '../../../models/motif';
import { CommonModule } from '@angular/common';
import {NgbModal, NgbModalRef} from "@ng-bootstrap/ng-bootstrap";

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

  constructor(private motifService: MotifService, private router: Router, private modalService: NgbModal) { }

  ngOnInit(): void {
    this.loadMotifs();
  }

  loadMotifs(): void {
    this.motifService.getMotifs().subscribe(data => {
      this.motifs = data;
      console.log('Motifs loaded:', this.motifs);
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
