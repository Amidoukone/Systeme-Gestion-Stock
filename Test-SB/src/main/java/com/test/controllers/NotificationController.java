package com.test.controllers;

import com.test.entities.Notification;
import com.test.services.NotificationService;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @GetMapping
    public List<Notification> getAllNotifications() {
        return notificationService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Notification> getNotificationById(@PathVariable int id) {
        return notificationService.findById(id)
                .map(notification -> ResponseEntity.ok().body(notification))
                .orElse(ResponseEntity.notFound().build());
    }

    @PatchMapping("/{id}/read")
    public ResponseEntity<Void> markAsRead(@PathVariable int id) {
        notificationService.markAsRead(id);
        return ResponseEntity.ok().build();
    }


    @PostMapping
    public Notification createNotification(@RequestBody Notification notification) {
        return notificationService.save(notification);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Notification> updateNotification(@PathVariable int id, @RequestBody Notification notificationDetails) {
        return notificationService.findById(id)
                .map(notification -> {
                    notification.setContenu(notificationDetails.getContenu());
                    notification.setDateNotif(notificationDetails.getDateNotif());
                    notification.setUtilisateur(notificationDetails.getUtilisateur());
                    Notification updatedNotification = notificationService.save(notification);
                    return ResponseEntity.ok().body(updatedNotification);
                }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNotification(@PathVariable int id) {
        notificationService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
