export interface Utilisateur {
  utilisateur_id: number;
  username: string;
  contact: string;
  email: string;
  password: string;
  entrepots_id: number;
  roles_id: number;
  roleName?: string;   // Nom du rôle pour affichage dans la liste
  entrepotName?: string; // Nom de l'entrepôt pour affichage dans la liste
}
