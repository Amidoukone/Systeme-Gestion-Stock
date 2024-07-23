import {Entrepot} from "./entrepot";
import {Role} from "./role";

export interface Utilisateur {
  id?: number;
  nom: string;
  contact: string;
  email: string;
  password: string;
  role?: Role;
  entrepot?: Entrepot;

}

