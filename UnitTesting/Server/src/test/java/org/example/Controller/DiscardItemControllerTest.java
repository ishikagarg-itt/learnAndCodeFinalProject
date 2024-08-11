package org.example.Controller;

import org.example.Entity.DiscardItem;
import org.example.Services.DiscardItemService;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class DiscardItemControllerTest {

    private DiscardItemController discardItemController;
    private DiscardItemService discardItemService;

    @Before
    public void setUp() {
        discardItemService = mock(DiscardItemService.class);
        discardItemController = new DiscardItemController();
        setField(discardItemController, "discardItemService", discardItemService);
    }

    @Test
    public void testGetDiscardItems() {
        List<DiscardItem> expectedDiscardItems = Arrays.asList(mock(DiscardItem.class), mock(DiscardItem.class));
        when(discardItemService.getDiscardItems()).thenReturn(expectedDiscardItems);

        List<DiscardItem> actualDiscardItems = discardItemController.getDiscardItems();

        assertEquals(expectedDiscardItems, actualDiscardItems);
        verify(discardItemService).getDiscardItems();
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
