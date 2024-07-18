export interface Notification {
  id: number;
  contenu: string;
  dateNotif: string;
  read: boolean;
  utilisateur: {
    id: number;
    username: string;
  };
}
