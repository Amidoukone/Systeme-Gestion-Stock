import {DetailSortie} from "./detail-sortie";
import {Utilisateur} from "./utilisateur";

export interface BonSortie {
  id: number;
  motif: string;
  date_sortie: Date;
  utilisateur: Utilisateur;
  detailsSorties: DetailSortie[];
}
