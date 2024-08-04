import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormArray, FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { BonSortie } from '../../../models/bon-sortie';
import { DetailSortie } from '../../../models/detail-sortie';
import { Produit } from '../../../models/produit';
import { AuthService } from '../../../services/auth.service';
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
  infoMessage= '';
  errorMessage= '';

  constructor(
    private fb: FormBuilder,
    private route: ActivatedRoute,
    private router: Router,
    private bonSortieService: BonSortieService,
    private produitService: ProduitService,
    private detailSortieService: DetailSortieService,
    private authService: AuthService
  ) {
    this.bonSortieId = +this.route.snapshot.paramMap.get('id')!;
    this.bonSortieForm = this.fb.group({
      details: this.fb.array([]),
    });
  }

  ngOnInit(): void {
    this.loadProduits();
    this.loadBonSortie();
    this.addDetail();
  }

  get details(): FormArray {
    return this.bonSortieForm.get('details') as FormArray;
  }

  loadProduits(): void {
    const currentUser = this.authService.currentUserValue;
    if (currentUser && currentUser.entrepot) {
      const entrepotId = currentUser.entrepot.entrepotId;
      this.produitService.getProduitsByEntrepot(entrepotId).subscribe(produits => {
        if (produits.length === 0) {
          this.infoMessage = 'Aucun produit trouvée pour cet Entrepot.';
          setTimeout(() => this.infoMessage = '', 2000);
        }else{

          this.produits = produits;
        }
      }, error => {
        console.error('Erreur lors de la récupération des produits:', error);
        this.errorMessage = 'Erreur lors de la récupération des produits.';
      });
    } else {
      this.errorMessage = 'Erreur: entrepôt utilisateur non trouvé';
    }
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
  // isDetailsPresent(): boolean {
  //   return this.details.length > 0;
  // }
}