import { DetailSortie} from './detail-sortie';

export interface BonSortie {
  id: number;
  dateCommande: Date;
  statut: string;
  utilisateur_id: number;
  detailSorties: DetailSortie[];
}
