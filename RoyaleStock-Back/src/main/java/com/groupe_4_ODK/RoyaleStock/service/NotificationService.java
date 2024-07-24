package com.groupe_4_ODK.RoyaleStock.service;

import com.groupe_4_ODK.RoyaleStock.entite.Notification;
import com.groupe_4_ODK.RoyaleStock.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class NotificationService {

  @Autowired
  private NotificationRepository notificationRepository;
  @Autowired
  private EmailService emailService;

  //Methode pour envoyer notification
  public void sendNotification(String email, String message) {
    // Créer et enregistrer la notification dans la base de données
    Notification notification = new Notification();
    notification.setContenu(message);
    notification.setDateNotification(new Date());
    notificationRepository.save(notification);

    // Envoyer l'email
    emailService.sendSimpleMessage(email, "Gestion  de Stock", message);
    notificationRepository.save(notification);

  }
}
