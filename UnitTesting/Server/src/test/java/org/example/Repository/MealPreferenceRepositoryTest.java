package org.example.Repository;

import org.example.Constants.DatabaseConstants;
import org.example.Mapper.MealPreferenceMapper;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import java.lang.reflect.Field;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

public class MealPreferenceRepositoryTest {

    private MealPreferenceRepository mealPreferenceRepository;
    private JdbcTemplate jdbcTemplate;

    @Before
    public void setUp() {
        mealPreferenceRepository = spy(MealPreferenceRepository.class);
        jdbcTemplate = mock(JdbcTemplate.class);
        setField(mealPreferenceRepository, "jdbcTemplate", jdbcTemplate);
    }

    @Test
    public void testGetByName() {
        String typeName = "Vegetarian";
        int expectedId = 1;

        when(jdbcTemplate.queryForObject(eq(DatabaseConstants.SELECT_MEAL_PREFERENCE_TYPE_BY_NAME), any(Object[].class), any(MealPreferenceMapper.class)))
                .thenReturn(expectedId);

        Optional<Integer> result = mealPreferenceRepository.getByName(typeName);

        assertEquals(Optional.of(expectedId), result);
        verify(jdbcTemplate).queryForObject(eq(DatabaseConstants.SELECT_MEAL_PREFERENCE_TYPE_BY_NAME), any(Object[].class), any(MealPreferenceMapper.class));
    }

    @Test
    public void testGetByNameThrowsNullPointerException() {
        String typeName = "NonExistent";

        when(jdbcTemplate.queryForObject(eq(DatabaseConstants.SELECT_MEAL_PREFERENCE_TYPE_BY_NAME), any(Object[].class), any(MealPreferenceMapper.class)))
                .thenReturn(null);

        assertThrows(NullPointerException.class, () -> {
            mealPreferenceRepository.getByName(typeName);
        });

        verify(jdbcTemplate).queryForObject(eq(DatabaseConstants.SELECT_MEAL_PREFERENCE_TYPE_BY_NAME), any(Object[].class), any(MealPreferenceMapper.class));
    }

    private void setField(Object target, String fieldName, Object value) {
        try {
            Field field = target.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(target, value);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
