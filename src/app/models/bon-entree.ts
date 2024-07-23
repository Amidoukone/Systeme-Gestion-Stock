import { Utilisateur } from './utilisateur';
import { Fournisseur } from './fournisseur';
import { DetailEntree } from './detail-entree';

export interface BonEntree {
  id: number;
  dateCommande: Date;
  statut: string;
  utilisateur_id: number;
  fournisseur_id: number;
  detailEntrees: DetailEntree[];
}
