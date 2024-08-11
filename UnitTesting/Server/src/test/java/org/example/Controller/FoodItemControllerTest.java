package org.example.Controller;

import org.example.Entity.FoodItem;
import org.example.Services.FoodItemService;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class FoodItemControllerTest {

    private FoodItemController foodItemController;
    private FoodItemService foodItemService;

    @Before
    public void setUp() {
        foodItemService = mock(FoodItemService.class);
        foodItemController = new FoodItemController();
        setField(foodItemController, "foodItemService", foodItemService);
    }

    @Test
    public void testGet() {
        int id = 1;
        FoodItem expectedFoodItem = mock(FoodItem.class);
        when(foodItemService.get(id)).thenReturn(expectedFoodItem);

        FoodItem actualFoodItem = foodItemController.get(id);

        assertEquals(expectedFoodItem, actualFoodItem);
        verify(foodItemService).get(id);
    }

    @Test
    public void testGetAll() {
        List<FoodItem> expectedFoodItems = Arrays.asList(mock(FoodItem.class), mock(FoodItem.class));
        when(foodItemService.getAll()).thenReturn(expectedFoodItems);

        List<FoodItem> actualFoodItems = foodItemController.getAll();

        assertEquals(expectedFoodItems, actualFoodItems);
        verify(foodItemService).getAll();
    }

    @Test
    public void testAdd() {
        FoodItem foodItem = mock(FoodItem.class);
        String expectedResponse = "Food item added successfully";
        when(foodItemService.add(foodItem)).thenReturn(expectedResponse);

        String actualResponse = foodItemController.add(foodItem);

        assertEquals(expectedResponse, actualResponse);
        verify(foodItemService).add(foodItem);
    }

    @Test
    public void testUpdate() {
        int id = 1;
        FoodItem foodItem = mock(FoodItem.class);
        FoodItem updatedFoodItem = mock(FoodItem.class);
        when(foodItemService.update(id, foodItem)).thenReturn(updatedFoodItem);

        FoodItem actualUpdatedFoodItem = foodItemController.update(id, foodItem);

        assertEquals(updatedFoodItem, actualUpdatedFoodItem);
        verify(foodItemService).update(id, foodItem);
    }

    @Test
    public void testDelete() {
        int id = 1;

        foodItemController.delete(id);

        verify(foodItemService).delete(id);
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
