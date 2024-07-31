import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormArray, FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { BonSortie } from '../../../models/bon-sortie';
import { DetailSortie } from '../../../models/detail-sortie';
import { Produit } from '../../../models/produit';
import { BonSortieService } from '../../../services/bon-sortie.service';
import { DetailSortieService } from '../../../services/detail-sortie.service';
import { ProduitService } from '../../../services/produit.service';

@Component({
  selector: 'app-bon-sortie-detail',
  standalone: true,
  imports: [CommonModule, FormsModule, ReactiveFormsModule],
  templateUrl: './bon-sortie-detail.component.html',
  styleUrls: ['./bon-sortie-detail.component.css']
})
export class BonSortieDetailComponent implements OnInit {
  bonSortieForm: FormGroup;
  produits: Produit[] = [];
  bonSortieId: number;

  constructor(
    private fb: FormBuilder,
    private route: ActivatedRoute,
    private router: Router,
    private bonSortieService: BonSortieService,
    private produitService: ProduitService,
    private detailSortieService: DetailSortieService
  ) {
    this.bonSortieId = +this.route.snapshot.paramMap.get('id')!;
    this.bonSortieForm = this.fb.group({
      details: this.fb.array([]),
    });
  }

  ngOnInit(): void {
    this.loadProduits();
    this.loadBonSortie();
  }

  get details(): FormArray {
    return this.bonSortieForm.get('details') as FormArray;
  }

  loadProduits(): void {
    this.produitService.getProduits().subscribe(data => {
      this.produits = data;
    });
  }

  loadBonSortie(): void {
    this.bonSortieService.getBonSortieById(this.bonSortieId).subscribe(data => {
      if (data && data.detailsSorties) {
        data.detailsSorties.forEach((detail: DetailSortie) => {
          this.addDetail(detail);
        });
      }
    });
  }

  addDetail(detail?: DetailSortie): void {
    this.details.push(this.fb.group({
      produit: [detail?.produit || '', Validators.required],
      quantity: [detail?.quantity || '', Validators.required],
      prix: [detail?.prix || '', Validators.required]
    }));
  }

  removeDetail(index: number): void {
    this.details.removeAt(index);
  }

  onSubmit(): void {
    const formValue = this.bonSortieForm.value;
    formValue.details.forEach((detail: DetailSortie) => {
      detail.bonSortie = { id: this.bonSortieId } as BonSortie;
      this.detailSortieService.createDetailSortie(detail).subscribe();
    });
    this.router.navigate(['/bon-sortie']);
  }
}
