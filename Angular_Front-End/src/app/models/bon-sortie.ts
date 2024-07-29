import {DetailSortie} from "./detail-sortie";
import {Utilisateur} from "./utilisateur";
import {Produit} from "./produit";
import {Motif} from "./motif";

export interface BonSortie {
  id: number;
  motif: Motif;
  dateSortie: Date;
  utilisateur: Utilisateur;
  detailsSorties: DetailSortie[];
}
