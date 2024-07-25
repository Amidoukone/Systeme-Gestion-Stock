import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { MotifService } from '../../../services/motif.service';
import { Motif } from '../../../models/motif';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-motif-list',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './motif-list.component.html',
  styleUrls: ['./motif-list.component.css']
})
export class MotifListComponent implements OnInit {
  motifs: Motif[] = [];

  constructor(private motifService: MotifService, private router: Router) { }

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
}
