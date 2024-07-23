import { Component, OnInit, NO_ERRORS_SCHEMA } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, Router } from '@angular/router';
import { EntrepotService } from '../../../services/entrepot.service';
import { Entrepot } from '../../../models/entrepot';
import {FormsModule} from "@angular/forms";

@Component({
  selector: 'app-entrepot-form',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './entrepot-form.component.html',
  styleUrl: './entrepot-form.component.css'
})
export class EntrepotFormComponent implements OnInit {
  entrepot: Entrepot = {} as Entrepot;
  isEditMode: boolean = false;

  constructor(
    private entrepotService: EntrepotService,
    private route: ActivatedRoute,
    private router: Router
  ) { }

  ngOnInit(): void {
    const id = this.route.snapshot.paramMap.get('id');
    if (id) {
      this.isEditMode = true;
      this.loadEntrepotById(+id);
    }
  }

  loadEntrepotById(id: number) {
    this.entrepotService.getEntrepotById(id).subscribe(data => {
      this.entrepot = data;
    });
  }

  onSubmit(event: Event): void {
    event.preventDefault();

    if (this.isEditMode) {
      this.entrepotService.updateEntrepot(this.entrepot.id, this.entrepot).subscribe(() => {
        this.router.navigate(['/entrepots']);
      });
    } else {
      this.entrepotService.createEntrepot(this.entrepot).subscribe(() => {
        this.router.navigate(['/entrepots']);
      });
    }
  }
}
