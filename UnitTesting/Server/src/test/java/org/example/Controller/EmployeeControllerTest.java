package org.example.Controller;

import org.example.Dto.EmployeeMenuDto;
import org.example.Dto.ProfileDto;
import org.example.Dto.RatingDto;
import org.example.Entity.Notification;
import org.example.Services.EmployeeService;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class EmployeeControllerTest {

    private EmployeeController employeeController;
    private EmployeeService employeeService;

    @Before
    public void setUp() {
        employeeService = mock(EmployeeService.class);
        employeeController = new EmployeeController();
        setField(employeeController, "employeeService", employeeService);
    }

    @Test
    public void testGetRollOutMenu() {
        String username = "testUser";
        List<EmployeeMenuDto> expectedMenu = Arrays.asList(mock(EmployeeMenuDto.class), mock(EmployeeMenuDto.class));
        when(employeeService.getRollOutMenu(username)).thenReturn(expectedMenu);

        List<EmployeeMenuDto> actualMenu = employeeController.getRollOutMenu(username);

        assertEquals(expectedMenu, actualMenu);
        verify(employeeService).getRollOutMenu(username);
    }

    @Test
    public void testChooseItems() {
        String username = "testUser";
        List<Integer> foodItemIds = Arrays.asList(1, 2, 3);
        String expectedResponse = "Items chosen successfully";
        when(employeeService.chooseItems(foodItemIds, username)).thenReturn(expectedResponse);

        String actualResponse = employeeController.chooseItems(foodItemIds, username);

        assertEquals(expectedResponse, actualResponse);
        verify(employeeService).chooseItems(foodItemIds, username);
    }

    @Test
    public void testProvideRating() {
        String username = "testUser";
        RatingDto ratingDto = mock(RatingDto.class);
        String expectedResponse = "Rating provided successfully";
        when(employeeService.provideRating(ratingDto, username)).thenReturn(expectedResponse);

        String actualResponse = employeeController.provideRating(ratingDto, username);

        assertEquals(expectedResponse, actualResponse);
        verify(employeeService).provideRating(ratingDto, username);
    }

    @Test
    public void testViewNotifications() {
        List<Notification> expectedNotifications = Arrays.asList(mock(Notification.class), mock(Notification.class));
        when(employeeService.viewNotifications()).thenReturn(expectedNotifications);

        List<Notification> actualNotifications = employeeController.viewNotifications();

        assertEquals(expectedNotifications, actualNotifications);
        verify(employeeService).viewNotifications();
    }

    @Test
    public void testProvideDiscardItemRating() {
        String username = "testUser";
        RatingDto ratingDto = mock(RatingDto.class);
        String expectedResponse = "Discard item rating provided successfully";
        when(employeeService.provideDiscardItemRating(ratingDto, username)).thenReturn(expectedResponse);

        String actualResponse = employeeController.provideDiscardItemRating(ratingDto, username);

        assertEquals(expectedResponse, actualResponse);
        verify(employeeService).provideDiscardItemRating(ratingDto, username);
    }

    @Test
    public void testUpdateProfile() {
        String username = "testUser";
        ProfileDto profileDto = mock(ProfileDto.class);
        String expectedResponse = "Profile updated successfully";
        when(employeeService.updateProfile(profileDto, username)).thenReturn(expectedResponse);

        String actualResponse = employeeController.updateProfile(profileDto, username);

        assertEquals(expectedResponse, actualResponse);
        verify(employeeService).updateProfile(profileDto, username);
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
