import { Utilisateur } from './utilisateur';
import { Fournisseur } from './fournisseur';
import { DetailEntree } from './detail-entree';
import {Produit} from "./produit";

export interface BonEntree {
  id: number;
  dateCommande: Date;
  statut: string;
  utilisateur: Utilisateur;
  fournisseur: Fournisseur;
  detailEntrees: DetailEntree[];
}
