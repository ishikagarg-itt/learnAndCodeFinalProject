package org.example.Services;

import org.example.Entity.Notification;
import org.example.Entity.NotificationType;
import org.example.Repository.NotificationRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.util.ReflectionUtils.setField;

@RunWith(MockitoJUnitRunner.class)
public class NotificationServiceTest {

    private NotificationService notificationService;
    private NotificationRepository notificationRepository;

    @Before
    public void setUp() {
        notificationService = spy(new NotificationService());

        notificationRepository = mock(NotificationRepository.class);

        setField(notificationService, "notificationRepository", notificationRepository);
    }

    @Test
    public void testSendNotification() {
        String type = "Add_Item";
        String message = "A new item has been added to the menu";
        int notificationTypeId = 1;

        NotificationType notificationType = new NotificationType();
        notificationType.setId(notificationTypeId);
        notificationType.setType(type);

        Notification notification = notificationService.buildNotification(type, message);
        notificationService.sendNotification(notification);
    }

    @Test
    public void testGetNotifications() {
        List<Notification> notifications = Arrays.asList(new Notification(), new Notification());
        when(notificationRepository.getNotificationsForCurrentDate()).thenReturn(notifications);

        List<Notification> result = notificationService.getNotifications();

        verify(notificationRepository).getNotificationsForCurrentDate(); // Verify method call
        Assert.assertNotNull(result);
        Assert.assertEquals(2, result.size());
    }

    // Helper method to set private fields via reflection
    private void setField(Object target, String fieldName, Object value) {
        try {
            java.lang.reflect.Field field = target.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(target, value);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
