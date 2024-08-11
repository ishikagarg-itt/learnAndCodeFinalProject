package org.example.Repository;

import org.example.Constants.DatabaseConstants;
import org.example.Mapper.NotificationTypeMapper;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

public class NotificationTypeRepositoryTest {

    private NotificationTypeRepository notificationTypeRepository;
    private JdbcTemplate jdbcTemplate;

    @Before
    public void setUp() {
        notificationTypeRepository = spy(NotificationTypeRepository.class);
        jdbcTemplate = mock(JdbcTemplate.class);

        setField(notificationTypeRepository, "jdbcTemplate", jdbcTemplate);
    }

    @Test
    public void testGetByNameFound() {
        String typeName = "Important";
        int expectedId = 1;

        when(jdbcTemplate.queryForObject(eq(DatabaseConstants.SELECT_NOTIFICATION_TYPE_BY_NAME),
                any(Object[].class),
                any(NotificationTypeMapper.class)))
                .thenReturn(expectedId);

        Optional<Integer> result = notificationTypeRepository.getByName(typeName);

        assertTrue(result.isPresent());
        assertEquals(expectedId, result.get().intValue());
        verify(jdbcTemplate).queryForObject(eq(DatabaseConstants.SELECT_NOTIFICATION_TYPE_BY_NAME),
                any(Object[].class),
                any(NotificationTypeMapper.class));
    }

    @Test
    public void testGetByNameNotFound() {
        String typeName = "NonExistent";

        when(jdbcTemplate.queryForObject(eq(DatabaseConstants.SELECT_NOTIFICATION_TYPE_BY_NAME),
                any(Object[].class),
                any(NotificationTypeMapper.class)))
                .thenReturn(null);

        assertThrows(NullPointerException.class, () -> {
            notificationTypeRepository.getByName(typeName);
        });

        verify(jdbcTemplate).queryForObject(eq(DatabaseConstants.SELECT_NOTIFICATION_TYPE_BY_NAME),
                any(Object[].class),
                any(NotificationTypeMapper.class));
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
