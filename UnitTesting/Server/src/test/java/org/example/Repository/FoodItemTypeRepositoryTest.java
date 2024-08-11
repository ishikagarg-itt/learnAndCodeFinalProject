package org.example.Repository;

import org.example.Config.MySqlDataSourceConfig;
import org.example.Constants.DatabaseConstants;
import org.example.Entity.FoodItemType;
import org.example.Mapper.FoodItemTypeMapper;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.jdbc.core.JdbcTemplate;

import java.lang.reflect.Field;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

public class FoodItemTypeRepositoryTest {

    private FoodItemTypeRepository foodItemTypeRepository;
    private JdbcTemplate jdbcTemplate;

    @Before
    public void setUp() {
        foodItemTypeRepository = spy(FoodItemTypeRepository.class);

        jdbcTemplate = mock(JdbcTemplate.class);

        setField(foodItemTypeRepository, "jdbcTemplate", jdbcTemplate);
    }

    @Test
    public void testGetByName() {
        String typeName = "Vegetarian";
        int expectedId = 1;

        when(jdbcTemplate.queryForObject(eq(DatabaseConstants.SELECT_FOOD_TYPE_BY_NAME),
                ArgumentMatchers.any(Object[].class),
                any(FoodItemTypeMapper.class))).thenReturn(expectedId);

        Optional<Integer> result = foodItemTypeRepository.getByName(typeName);

        assertTrue(result.isPresent());
        assertEquals(expectedId, result.get().intValue());

        verify(jdbcTemplate).queryForObject(eq(DatabaseConstants.SELECT_FOOD_TYPE_BY_NAME),
                ArgumentMatchers.any(Object[].class),
                any(FoodItemTypeMapper.class));
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
