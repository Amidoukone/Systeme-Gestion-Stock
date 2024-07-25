import { Produit } from './produit';
import { BonEntree } from './bon-entree';

export interface DetailEntree {
  id: number;
  quantite: number;
  prix: number;
  produit: Produit;
  produit_id: number;
  bon_entree_id: number;
}
