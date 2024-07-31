import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormArray, FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { BonSortie } from '../../../models/bon-sortie';
import { DetailSortie } from '../../../models/detail-sortie';
import { Produit } from '../../../models/produit';
<<<<<<< HEAD
import { BonSortie } from '../../../models/bon-sortie';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
=======
import { BonSortieService } from '../../../services/bon-sortie.service';
import { DetailSortieService } from '../../../services/detail-sortie.service';
import { ProduitService } from '../../../services/produit.service';
>>>>>>> ec8787864eaeb91d7746925b8d3e19e69f11f6e4

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
  successMessage: string = '';
  errorMessage: string = '';

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
    this.produitService.getProduits().subscribe(
      data => this.produits = data,
      error => {
        console.error('Error loading produits:', error);
        this.errorMessage = 'Erreur lors du chargement des produits.';
        setTimeout(() => this.errorMessage = '', 3000);
      }
    );
  }

  loadBonSortie(): void {
<<<<<<< HEAD
    this.bonSortieService.getBonSortieById(this.bonSortieId).subscribe(
      data => {
        this.bonSortie = data;
        if (!this.bonSortie.detailsSorties) {
          this.bonSortie.detailsSorties = [];
        }
      },
      error => {
        console.error('Error loading bon sortie:', error);
        this.errorMessage = 'Erreur lors du chargement du bon de sortie.';
        setTimeout(() => this.errorMessage = '', 3000);
=======
    this.bonSortieService.getBonSortieById(this.bonSortieId).subscribe(data => {
      if (data && data.detailsSorties) {
        data.detailsSorties.forEach((detail: DetailSortie) => {
          this.addDetail(detail);
        });
>>>>>>> ec8787864eaeb91d7746925b8d3e19e69f11f6e4
      }
    );
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
<<<<<<< HEAD
    this.detailSortie.bonSortie = this.bonSortie;
    this.detailSortieService.createDetailSortie(this.detailSortie).subscribe(
      () => {
        this.successMessage = 'Détail ajouté avec succès!';
        setTimeout(() => this.successMessage = '', 3000);
        this.router.navigate(['/bon-sortie']);
      },
      error => {
        console.error('Error saving detail sortie:', error);
        this.errorMessage = 'Erreur lors de l\'ajout du détail.';
        setTimeout(() => this.errorMessage = '', 3000);
      }
    );
=======
    const formValue = this.bonSortieForm.value;
    formValue.details.forEach((detail: DetailSortie) => {
      detail.bonSortie = { id: this.bonSortieId } as BonSortie;
      this.detailSortieService.createDetailSortie(detail).subscribe();
    });
    this.router.navigate(['/bon-sortie']);
>>>>>>> ec8787864eaeb91d7746925b8d3e19e69f11f6e4
  }
}
