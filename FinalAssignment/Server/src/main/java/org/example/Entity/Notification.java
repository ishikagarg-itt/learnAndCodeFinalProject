package org.example.Entity;

import org.joda.time.DateTime;

public class Notification {
    private int id;
    private NotificationType notificationType;
    private DateTime notificationDate;
    private String message;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public NotificationType getNotificationType() {
        return notificationType;
    }

    public void setNotificationType(NotificationType notificationType) {
        this.notificationType = notificationType;
    }

    public DateTime getNotificationDate() {
        return notificationDate;
    }

    public void setNotificationDate(DateTime notificationDate) {
        this.notificationDate = notificationDate;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
