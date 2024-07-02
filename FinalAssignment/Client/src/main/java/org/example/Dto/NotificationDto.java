package org.example.Dto;

public class NotificationDto {
    private NotificationTypeDto notificationType;
    private String message;


    public NotificationTypeDto getNotificationType() {
        return notificationType;
    }

    public void setNotificationType(NotificationTypeDto notificationType) {
        this.notificationType = notificationType;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
