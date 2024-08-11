package org.example.Controller;

import org.example.Entity.FoodItem;
import org.example.Services.ChefService;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class ChefControllerTest {

    private ChefController chefController;
    private ChefService chefService;

    @Before
    public void setUp() {
        chefService = mock(ChefService.class);
        chefController = new ChefController();
        setField(chefController, "chefService", chefService);
    }

    @Test
    public void testGetRecommendation() {
        List<FoodItem> expectedFoodItems = Arrays.asList(mock(FoodItem.class), mock(FoodItem.class));
        when(chefService.getRecommendation()).thenReturn(expectedFoodItems);

        List<FoodItem> actualFoodItems = chefController.getRecommendation();

        assertEquals(expectedFoodItems, actualFoodItems);
        verify(chefService).getRecommendation();
    }

    @Test
    public void testRolloutMenuSucceeds() {
        List<Integer> foodItemIds = Arrays.asList(1, 2, 3);
        String expectedResponse = "Menu rolled out successfully";
        when(chefService.rollOutMenu(foodItemIds)).thenReturn(expectedResponse);

        String actualResponse = chefController.rolloutMenu(foodItemIds);

        assertEquals(expectedResponse, actualResponse);
        verify(chefService).rollOutMenu(foodItemIds);
    }

    @Test
    public void testAskFeedbackSucceeds() {
        String expectedFeedback = "Feedback requested";
        when(chefService.askFeedback()).thenReturn(expectedFeedback);

        String actualFeedback = chefController.askFeedBack();

        assertEquals(expectedFeedback, actualFeedback);
        verify(chefService).askFeedback();
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
