import { Produit } from './produit';
import { BonSortie } from './bon-sortie';
export interface DetailSortie {
  details_sortie_id: number;
  quantity: number;
  prix_total: number;
  date_expiration: Date;
  bon_sortie_id: number;
  produit_id: number;
}
