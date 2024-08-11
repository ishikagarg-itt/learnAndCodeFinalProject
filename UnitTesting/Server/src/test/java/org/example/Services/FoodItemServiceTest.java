package org.example.Services;

import org.example.Entity.FoodItem;
import org.example.Entity.ItemAudit;
import org.example.Entity.Notification;
import org.example.Repository.FoodItemRepository;
import org.example.Repository.ItemAuditRepository;
import org.example.Repository.NotificationRepository;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class FoodItemServiceTest {

    private FoodItemRepository foodItemRepository;

    private ItemAuditRepository itemAuditRepository;

    private NotificationRepository notificationRepository;

    private NotificationService notificationService;
    private FoodItemService foodItemService;

    @Before
    public void setUp() throws NoSuchFieldException, IllegalAccessException {
        foodItemService = spy(new FoodItemService());

        notificationRepository = mock(NotificationRepository.class);
        itemAuditRepository =mock(ItemAuditRepository.class);
        foodItemRepository = mock(FoodItemRepository.class);
        notificationService = mock(NotificationService.class);

        setField(notificationService, "notificationRepository", notificationRepository);
        setField(foodItemService, "notificationService", notificationService);
        setField(foodItemService, "itemAuditRepository", itemAuditRepository);
        setField(foodItemService, "foodItemRepository", foodItemRepository);
    }

    @Test
    public void testAddFoodItemSucceeds() {
        FoodItem foodItem = new FoodItem();
        foodItem.setId(1);

        when(foodItemRepository.save(foodItem)).thenReturn(foodItem);
        doNothing().when(itemAuditRepository).save(any(ItemAudit.class));

        Notification mockNotification = new Notification();
        when(notificationService.buildNotification(anyString(), anyString())).thenReturn(mockNotification);
        doNothing().when(notificationService).sendNotification(mockNotification);

        String result = foodItemService.add(foodItem);

        verify(foodItemRepository, times(1)).save(foodItem);
        verify(itemAuditRepository, times(1)).save(any(ItemAudit.class));
        verify(notificationService, times(1)).sendNotification(mockNotification);

        assertEquals("Item has been added successfully", result);
    }

    @Test
    public void testGetFoodItemSucceeds() {
        int id = 1;
        FoodItem foodItem = new FoodItem();

        when(foodItemRepository.getById(id)).thenReturn(foodItem);

        FoodItem result = foodItemService.get(id);

        verify(foodItemRepository, times(1)).getById(id);
        assertEquals(foodItem, result);
    }

    @Test
    public void testGetAllFoodItemsSucceeds() {
        List<FoodItem> foodItems = new ArrayList<>();
        foodItems.add(new FoodItem());

        when(foodItemRepository.getAll()).thenReturn(foodItems);

        List<FoodItem> result = foodItemService.getAll();

        verify(foodItemRepository, times(1)).getAll();
        assertEquals(foodItems, result);
    }

    @Test
    public void testDeleteFoodItemSucceeds() {
        int id = 1;

        doNothing().when(foodItemRepository).delete(id);

        foodItemService.delete(id);

        verify(foodItemRepository, times(1)).delete(id);
    }

    @Test
    public void testUpdateFoodItemSucceeds() {
        int id = 1;
        FoodItem foodItem = new FoodItem();

        when(foodItemRepository.update(eq(id), any(FoodItem.class))).thenReturn(foodItem);

        FoodItem result = foodItemService.update(id, foodItem);

        verify(foodItemRepository, times(1)).update(eq(id), any(FoodItem.class));
        assertEquals(foodItem, result);
    }

    private void setField(Object target, String fieldName, Object value) throws NoSuchFieldException, IllegalAccessException {
        Field field = target.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(target, value);
    }
}
