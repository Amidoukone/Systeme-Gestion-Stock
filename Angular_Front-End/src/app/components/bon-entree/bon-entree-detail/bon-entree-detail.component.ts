import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormArray, FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { BonEntree } from '../../../models/bon-entree';
import { DetailEntree } from '../../../models/detail-entree';
import { Produit } from '../../../models/produit';
import { BonEntreeService } from '../../../services/bon-entree.service';
import { DetailEntreeService } from '../../../services/detail-entree.service';
import { ProduitService } from '../../../services/produit.service';

@Component({
  selector: 'app-bon-entree-detail',
  standalone: true,
  imports: [CommonModule, FormsModule, ReactiveFormsModule],
  templateUrl: './bon-entree-detail.component.html',
  styleUrls: ['./bon-entree-detail.component.css']
})
export class BonEntreeDetailComponent implements OnInit {
  bonEntreeForm: FormGroup;
  produits: Produit[] = [];
  bonEntreeId: number;

  constructor(
    private fb: FormBuilder,
    private route: ActivatedRoute,
    private router: Router,
    private bonEntreeService: BonEntreeService,
    private produitService: ProduitService,
    private detailEntreeService: DetailEntreeService
  ) {
    this.bonEntreeId = +this.route.snapshot.paramMap.get('id')!;
    this.bonEntreeForm = this.fb.group({
      details: this.fb.array([]),
    });
  }

  ngOnInit(): void {
    this.loadProduits();
    this.loadBonEntree();
  }

  get details(): FormArray {
    return this.bonEntreeForm.get('details') as FormArray;
  }

  loadProduits(): void {
    this.produitService.getProduits().subscribe(data => {
      this.produits = data;
    });
  }

  loadBonEntree(): void {
    this.bonEntreeService.getBonEntreeById(this.bonEntreeId).subscribe(data => {
      if (data && data.detailEntrees) {
        data.detailEntrees.forEach((detail: DetailEntree) => {
          this.addDetail(detail);
        });
      }
    });
  }

  addDetail(detail?: DetailEntree): void {
    this.details.push(this.fb.group({
      produit: [detail?.produit || '', Validators.required],
      quantite: [detail?.quantite || '', Validators.required],
      prix: [detail?.prix || '', Validators.required]
    }));
  }

  removeDetail(index: number): void {
    this.details.removeAt(index);
  }

  onSubmit(): void {
    const formValue = this.bonEntreeForm.value;
    formValue.details.forEach((detail: DetailEntree) => {
      detail.bonEntree = { id: this.bonEntreeId } as BonEntree;
      this.detailEntreeService.createDetailEntree(detail).subscribe();
    });
    this.router.navigate(['/bon-entree']);
  }
}
