import {Produit} from "./produit";

export interface DetailSortie {
  id: number;
  quantity: number;
  prix_total: number;
  date_expiration: Date;
  bon_sortie_id: number;
  produit: Produit;
  produit_id: number;
}
