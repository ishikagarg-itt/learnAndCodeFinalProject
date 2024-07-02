package org.example.Services;

import org.example.Entity.Notification;
import org.example.Entity.NotificationType;
import org.example.Repository.NotificationRepository;
import org.example.Repository.NotificationTypeRepository;

import java.util.List;

public class NotificationService {

    private final NotificationRepository notificationRepository;
    private final NotificationTypeRepository notificationTypeRepository;

    public NotificationService(){
        notificationRepository = new NotificationRepository();
        notificationTypeRepository = new NotificationTypeRepository();
    }

    public void sendNotification(Notification notification){
        notificationRepository.save(notification);
    }

    public Notification buildNotification(String type, String message){
        Notification notification = new Notification();
        notification.setMessage(message);
        NotificationType notificationType = new NotificationType();
        notificationType.setId(notificationTypeRepository.getByName(type).get());
        notificationType.setType(type);
        notification.setNotificationType(notificationType);
        return notification;
    }

    public List<Notification> getNotifications(){
        return notificationRepository.getNotificationsForCurrentDate();
    }
}
