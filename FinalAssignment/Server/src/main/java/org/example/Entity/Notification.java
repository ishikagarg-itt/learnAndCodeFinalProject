package org.example.Entity;

public class Notification {
    private Long id;
    private NotificationType notificationType;
    private int expiryDuration;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public NotificationType getNotificationType() {
        return notificationType;
    }

    public void setNotificationType(NotificationType notificationType) {
        this.notificationType = notificationType;
    }

    public int getExpiryDuration() {
        return expiryDuration;
    }

    public void setExpiryDuration(int expiryDuration) {
        this.expiryDuration = expiryDuration;
    }
}
