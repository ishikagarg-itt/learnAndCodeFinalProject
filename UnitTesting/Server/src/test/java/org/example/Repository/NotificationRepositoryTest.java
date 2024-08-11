package org.example.Repository;

import org.example.Constants.DatabaseConstants;
import org.example.Entity.Notification;
import org.example.Entity.NotificationType;
import org.example.Mapper.NotificationMapper;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

public class NotificationRepositoryTest {

    private NotificationRepository notificationRepository;
    private JdbcTemplate jdbcTemplate;

    @Before
    public void setUp() {
        notificationRepository = spy(NotificationRepository.class);
        jdbcTemplate = mock(JdbcTemplate.class);

        setField(notificationRepository, "jdbcTemplate", jdbcTemplate);
    }

    @Test
    public void testSaveSuccess() {
        Notification notification = new Notification();
        notification.setMessage("Test Notification");
        NotificationType notificationType = new NotificationType();
        notificationType.setId(1);
        notification.setNotificationType(notificationType);

        when(jdbcTemplate.update(eq(DatabaseConstants.INSERT_NOTIFICATION),
                any(String.class),
                any(Integer.class)))
                .thenReturn(1);

        notificationRepository.save(notification);

        verify(jdbcTemplate).update(eq(DatabaseConstants.INSERT_NOTIFICATION),
                eq(notification.getMessage()),
                eq(notification.getNotificationType().getId()));
    }

    @Test
    public void testSaveFailure() {
        Notification notification = new Notification();
        notification.setMessage("Test Notification");
        NotificationType notificationType = new NotificationType();
        notificationType.setId(1);
        notification.setNotificationType(notificationType);

        when(jdbcTemplate.update(eq(DatabaseConstants.INSERT_NOTIFICATION),
                any(String.class),
                any(Integer.class)))
                .thenReturn(0);

        RuntimeException thrown = assertThrows(RuntimeException.class, () -> {
            notificationRepository.save(notification);
        });

        assertEquals("Failed to insert notification into the database", thrown.getMessage());
    }

    @Test
    public void testGetNotificationsForCurrentDateSuccess() {
        Notification notification = new Notification();
        notification.setMessage("Test Notification");
        NotificationType notificationType = new NotificationType();
        notificationType.setId(1);
        notification.setNotificationType(notificationType);

        when(jdbcTemplate.query(eq(DatabaseConstants.SELECT_NOTIFICATIONS_FOR_TODAY),
                any(NotificationMapper.class)))
                .thenReturn(Arrays.asList(notification));

        List<Notification> notifications = notificationRepository.getNotificationsForCurrentDate();

        assertEquals(1, notifications.size());
        assertEquals("Test Notification", notifications.get(0).getMessage());
    }

    @Test
    public void testGetNotificationsForCurrentDateFailure() {
        when(jdbcTemplate.query(eq(DatabaseConstants.SELECT_NOTIFICATIONS_FOR_TODAY),
                any(NotificationMapper.class)))
                .thenThrow(new DataAccessException("Database error") {});

        RuntimeException thrown = assertThrows(RuntimeException.class, () -> {
            notificationRepository.getNotificationsForCurrentDate();
        });

        assertEquals("Database error occurred", thrown.getMessage());
    }

    private void setField(Object target, String fieldName, Object value) {
        try {
            java.lang.reflect.Field field = target.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(target, value);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
