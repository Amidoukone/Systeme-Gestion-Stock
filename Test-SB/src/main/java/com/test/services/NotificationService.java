package com.test.services;

import com.test.entities.Notification;
import com.test.repositories.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;

    public List<Notification> findAll() {
        return notificationRepository.findAll();
    }

    public Optional<Notification> findById(int id) {
        return notificationRepository.findById(id);
    }


    public void markAsRead(int id) {
        Notification notification = notificationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Notification non trouvé"));
        notification.setRead(true);  // Met à jour le champ isRead
        notificationRepository.save(notification);
    }


    public Notification save(Notification notification) {
        return notificationRepository.save(notification);
    }

    public void deleteById(int id) {
        notificationRepository.deleteById(id);
    }
}
