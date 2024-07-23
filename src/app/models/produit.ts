import { Categorie } from './categorie';

/*
export interface Produit {
  id: number;
  productName: string;
  description: string;
  prix: number;
  quantity: number;
  categories_id: number;
  categoryName?: string; // Pour afficher le nom de la catégorie dans la liste des produits
}

 */

export class Produit {
  id!: number;
  productName!: string;
  description!: string;
  prix!: number;
  quantity!: number;
  categorie!: Categorie;
}


