import { Utilisateur } from './utilisateur';
import { Fournisseur } from './fournisseur';
import { DetailEntree } from './detail-entree';
import {Produit} from "./produit";

export interface BonEntree {
  id: number;
  dateCommande: Date;
  statut: string;
  utilisateur: {
    id: number;
    username: string;
  };
  utilisateur_id: number;
  fournisseur: {
    id: number;
    fournName: string;
  };
  fournisseur_id: number;
  detailEntrees: DetailEntree[];
}
