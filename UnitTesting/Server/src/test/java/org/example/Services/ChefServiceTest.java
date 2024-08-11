package org.example.Services;

import org.example.Entity.FoodItem;
import org.example.Entity.Notification;
import org.example.Entity.RolloutMenuItem;
import org.example.Exception.NotFoundException;
import org.example.Repository.FoodItemRepository;
import org.example.Repository.RolloutMenuItemRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ChefServiceTest {

    @Mock
    private RecommendationService recommendationService;

    @Mock
    private NotificationService notificationService;

    @Mock
    private FoodItemRepository foodItemRepository;

    @Mock
    private RolloutMenuItemRepository rolloutMenuRepository;

    @InjectMocks
    private ChefService chefService;

    @Test
    public void testGetRecommendationSucceeds() {
        List<FoodItem> expectedFoodItems = Collections.nCopies(15, new FoodItem());
        when(recommendationService.getRecommendation()).thenReturn(expectedFoodItems);

        List<FoodItem> foodItems = chefService.getRecommendation();

        assertEquals(expectedFoodItems.size(), foodItems.size());
        verify(recommendationService, times(1)).getRecommendation();
    }

    @Test
    public void testRollOutMenuSucceeds() {
        List<Integer> foodItemIds = Arrays.asList(1, 2);
        when(foodItemRepository.isExist(1)).thenReturn(true);
        when(foodItemRepository.isExist(2)).thenReturn(true);

        Notification notification = new Notification();
        when(notificationService.buildNotification(eq("Roll_Out"), anyString())).thenReturn(notification);

        String result = chefService.rollOutMenu(foodItemIds);

        assertEquals("Menu rolled out successfully and notification sent to the employees", result);
        verify(foodItemRepository, times(1)).isExist(1);
        verify(foodItemRepository, times(1)).isExist(2);
        verify(rolloutMenuRepository, times(2)).save(any(RolloutMenuItem.class));
        verify(notificationService, times(1)).sendNotification(notification);
    }

    @Test(expected = NotFoundException.class)
    public void testRollOutMenuFails() {
        List<Integer> foodItemIds = Arrays.asList(1);
        when(foodItemRepository.isExist(1)).thenReturn(false);

        chefService.rollOutMenu(foodItemIds);
    }

    @Test
    public void testAskFeedbackSucceeds() {
        Notification notification = new Notification();
        when(notificationService.buildNotification(eq("Discard_Item"), anyString())).thenReturn(notification);

        String result = chefService.askFeedback();

        assertEquals("You have successfully sent notification for asking the feedback", result);
        verify(notificationService, times(1)).sendNotification(notification);
    }
}
