package org.example.Repository;

import org.example.Entity.FoodItem;
import org.example.Entity.FoodItemType;
import org.example.Entity.MealPreference;
import org.example.Entity.Region;
import org.example.Entity.SpiceLevel;
import org.example.Exception.NotFoundException;
import org.example.Mapper.FoodItemMapper;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.lang.reflect.Field;
import java.sql.PreparedStatement;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.example.Constants.DatabaseConstants.*;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

public class FoodItemRepositoryTest {

    private FoodItemRepository foodItemRepository;
    private JdbcTemplate jdbcTemplate;

    @Before
    public void setUp() {
        foodItemRepository = spy(FoodItemRepository.class);

        jdbcTemplate = mock(JdbcTemplate.class);

        setField(foodItemRepository, "jdbcTemplate", jdbcTemplate);
    }

    @Test
    public void testSave() {
        FoodItem foodItem = new FoodItem();
        foodItem.setName("Test Food");
        foodItem.setAvailabilityStatus(true);
        foodItem.setType(new FoodItemType());
        foodItem.setMealPreference(new MealPreference());
        foodItem.setRegion(new Region());
        foodItem.setSpiceLevel(new SpiceLevel());

        when(jdbcTemplate.update(any(PreparedStatementCreator.class), any(KeyHolder.class))).thenAnswer(invocation -> {
            KeyHolder kh = invocation.getArgument(1);

            Map<String, Object> generatedKey = Map.of("GENERATED_KEY", 1);
            kh.getKeyList().add(generatedKey);
            return 1;
        });

        FoodItem savedItem = foodItemRepository.save(foodItem);

        assertNotNull(savedItem.getId());
        assertEquals(1, savedItem.getId());
        verify(jdbcTemplate).update(any(PreparedStatementCreator.class), any(KeyHolder.class));
    }


    @Test(expected = NotFoundException.class)
    public void testGetById_NotFound() {
        when(jdbcTemplate.queryForObject(eq(SELECT_FOOD_ITEM_BY_ID), any(Object[].class), any(FoodItemMapper.class)))
                .thenThrow(new EmptyResultDataAccessException(1));

        foodItemRepository.getById(1);
    }

    @Test
    public void testUpdate() {
        FoodItem foodItem = new FoodItem();
        foodItem.setName("Updated Food");
        foodItem.setType(new FoodItemType());
        foodItem.setMealPreference(new MealPreference());
        foodItem.setRegion(new Region());
        foodItem.setSpiceLevel(new SpiceLevel());

        when(jdbcTemplate.update(eq(UPDATE_FOOD_ITEM), any(Object[].class)))
                .thenReturn(1);

        FoodItem updatedItem = foodItemRepository.update(1, foodItem);

        assertEquals("Updated Food", updatedItem.getName());
        verify(jdbcTemplate).update(eq(UPDATE_FOOD_ITEM), any(Object[].class));
    }

    @Test(expected = NotFoundException.class)
    public void testUpdate_NotFound() {
        FoodItem foodItem = new FoodItem();
        foodItem.setType(new FoodItemType());
        foodItem.setMealPreference(new MealPreference());
        foodItem.setRegion(new Region());
        foodItem.setSpiceLevel(new SpiceLevel());

        when(jdbcTemplate.update(eq(UPDATE_FOOD_ITEM), any(Object[].class)))
                .thenReturn(0);

        foodItemRepository.update(1, foodItem);
    }

    @Test
    public void testDelete() {
        when(jdbcTemplate.update(eq(DELETE_FOOD_ITEM), eq(1)))
                .thenReturn(1); // Simulate one row deleted

        foodItemRepository.delete(1);

        verify(jdbcTemplate).update(eq(DELETE_FOOD_ITEM), eq(1));
    }

    @Test(expected = NotFoundException.class)
    public void testDelete_NotFound() {
        when(jdbcTemplate.update(eq(DELETE_FOOD_ITEM), eq(1)))
                .thenReturn(0); // Simulate no rows deleted

        foodItemRepository.delete(1);
    }

    @Test
    public void testGetAll() {
        when(jdbcTemplate.query(eq(SELECT_ALL_FOOD_ITEMS), any(FoodItemMapper.class)))
                .thenReturn(Arrays.asList(mock(FoodItem.class), mock(FoodItem.class)));

        List<FoodItem> foodItems = foodItemRepository.getAll();

        assertEquals(2, foodItems.size());
        verify(jdbcTemplate).query(eq(SELECT_ALL_FOOD_ITEMS), any(FoodItemMapper.class));
    }

    @Test
    public void testIsExist() {
        when(jdbcTemplate.queryForObject(eq(COUNT_FOOD_ITEM_BY_ID), any(Object[].class), eq(Integer.class)))
                .thenReturn(1); // Simulate food item exists

        boolean exists = foodItemRepository.isExist(1);

        assertTrue(exists);
        verify(jdbcTemplate).queryForObject(eq(COUNT_FOOD_ITEM_BY_ID), any(Object[].class), eq(Integer.class));
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
