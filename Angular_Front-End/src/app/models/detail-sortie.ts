import {Produit} from "./produit";
import {Categorie} from "./categorie";

export interface DetailSortie {
  id: number;
  quantity: number;
  prix: number;
  bon_sortie_id: number;
  produit: Produit;
  produit_id: number;
}
