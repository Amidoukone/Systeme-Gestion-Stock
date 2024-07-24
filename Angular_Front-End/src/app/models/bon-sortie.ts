import {DetailSortie} from "./detail-sortie";
import {Utilisateur} from "./utilisateur";
import {Produit} from "./produit";

export interface BonSortie {
  id: number;
  motif: {
    id: number,
    title: string,
    createBy:number
  };
  dateSortie: Date;
  utilisateur: {
    id: number;
    username: string;
  };
  utilisateur_id: number;
  detailsSorties: DetailSortie[];
}
