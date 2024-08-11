package org.example.Services;

import org.example.Dto.EmployeeMenuDto;
import org.example.Dto.ProfileDto;
import org.example.Dto.RatingDto;
import org.example.Entity.Notification;
import org.example.Entity.Profile;
import org.example.Entity.Rating;
import org.example.Entity.VotedItem;
import org.example.Exception.NotFoundException;
import org.example.Repository.FoodItemRepository;
import org.example.Repository.MealPreferenceRepository;
import org.example.Repository.ProfileRepository;
import org.example.Repository.RatingRepository;
import org.example.Repository.RegionRepository;
import org.example.Repository.RolloutMenuItemRepository;
import org.example.Repository.SpiceLevelRepository;
import org.example.Repository.VotedItemRepository;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class EmployeeServiceTest {
    private VotedItemRepository votedItemRepository;
    private RatingRepository ratingRepository;
    private FoodItemRepository foodItemRepository;
    private NotificationService notificationService;
    private RecommendationService recommendationService;
    private RolloutMenuItemRepository rolloutMenuRepository;
    private ProfileRepository profileRepository;
    private MealPreferenceRepository mealPreferenceRepository;
    private SpiceLevelRepository spiceLevelRepository;
    private RegionRepository regionRepository;
    private EmployeeService employeeService;

    @Before
    public void setUp() throws NoSuchFieldException, IllegalAccessException {
        employeeService = spy(new EmployeeService());

        notificationService = mock(NotificationService.class);
        votedItemRepository = mock(VotedItemRepository.class);
        ratingRepository = mock(RatingRepository.class);
        foodItemRepository = mock(FoodItemRepository.class);
        recommendationService = mock(RecommendationService.class);
        profileRepository = mock(ProfileRepository.class);
        mealPreferenceRepository = mock(MealPreferenceRepository.class);
        spiceLevelRepository = mock(SpiceLevelRepository.class);
        rolloutMenuRepository = mock(RolloutMenuItemRepository.class);
        regionRepository = mock(RegionRepository.class);


        setField(employeeService, "notificationService", notificationService);
        setField(employeeService, "votedItemRepository", votedItemRepository);
        setField(employeeService, "ratingRepository", ratingRepository);
        setField(employeeService, "foodItemRepository", foodItemRepository);
        setField(employeeService, "recommendationService", recommendationService);
        setField(employeeService, "rolloutMenuRepository", rolloutMenuRepository);
        setField(employeeService, "profileRepository", profileRepository);
        setField(employeeService, "mealPreferenceRepository", mealPreferenceRepository);
        setField(employeeService, "spiceLevelRepository", spiceLevelRepository);
        setField(employeeService, "regionRepository", regionRepository);
    }

    @Test
    public void testGetRollOutMenuSucceeds() {
        String username = "john.doe";
        Profile profile = new Profile();
        List<EmployeeMenuDto> mockMenu = new ArrayList<>();

        when(profileRepository.getUserProfile(username)).thenReturn(profile);
        when(rolloutMenuRepository.getMenuForEmployee(username, profile)).thenReturn(mockMenu);

        List<EmployeeMenuDto> result = employeeService.getRollOutMenu(username);

        assertEquals(mockMenu, result);
        verify(profileRepository, times(1)).getUserProfile(username);
        verify(rolloutMenuRepository, times(1)).getMenuForEmployee(username, profile);
    }

    @Test
    public void testChooseItemsSucceeds() {
        String username = "john.doe";
        List<Integer> chosenFoodItemIds = List.of(1, 2, 3);

        when(rolloutMenuRepository.isFoodItemRolledOutToday(anyInt())).thenReturn(true);
        when(votedItemRepository.hasUserVotedForFoodItemToday(eq(username), anyInt())).thenReturn(false);

        String result = employeeService.chooseItems(chosenFoodItemIds, username);

        assertEquals("You have chosen the items to be prepared for tomorrow", result);
        verify(rolloutMenuRepository, times(chosenFoodItemIds.size())).isFoodItemRolledOutToday(anyInt());
        verify(votedItemRepository, times(chosenFoodItemIds.size())).save(any(VotedItem.class));
    }

    @Test(expected = NotFoundException.class)
    public void testChooseItemsWithFoodItemNotRolledOut() {
        String username = "john.doe";
        List<Integer> chosenFoodItemIds = List.of(1);

        when(rolloutMenuRepository.isFoodItemRolledOutToday(anyInt())).thenReturn(false);

        employeeService.chooseItems(chosenFoodItemIds, username);
    }

    @Test
    public void testProvideRatingSucceeds() {
        String username = "ishika.garg";
        RatingDto ratingDto = new RatingDto();
        ratingDto.setFoodItemId(1);
        ratingDto.setComment("food was very good");

        when(foodItemRepository.isExist(ratingDto.getFoodItemId())).thenReturn(true);
        when(ratingRepository.hasUserRatedToday(username, ratingDto.getFoodItemId())).thenReturn(false);

        String result = employeeService.provideRating(ratingDto, username);

        assertEquals("You have rated the item successfully", result);
        verify(ratingRepository, times(1)).save(any(Rating.class), eq(username));
        verify(recommendationService, times(1)).updateItemAudit(ratingDto.getFoodItemId());
    }

    @Test(expected = NotFoundException.class)
    public void testProvideRatingWithFoodItemNotFound() {
        String username = "john.doe";
        RatingDto ratingDto = new RatingDto();
        ratingDto.setFoodItemId(7000);

        when(foodItemRepository.isExist(ratingDto.getFoodItemId())).thenReturn(false);

        employeeService.provideRating(ratingDto, username);
    }

    @Test
    public void testViewNotificationsSucceeds() {
        List<Notification> mockNotifications = new ArrayList<>();

        when(notificationService.getNotifications()).thenReturn(mockNotifications);

        List<Notification> result = employeeService.viewNotifications();

        assertEquals(mockNotifications, result);
        verify(notificationService, times(1)).getNotifications();
    }

    @Test
    public void testUpdateProfileSucceeds() {
        String username = "ishika.garg";
        ProfileDto profileDto = new ProfileDto();
        profileDto.setRegion("North Indian");
        profileDto.setMealPreference("Vegetarian");
        profileDto.setSpiceLevel("Medium");

        when(regionRepository.getByName(anyString())).thenReturn(Optional.of(1));
        when(mealPreferenceRepository.getByName(anyString())).thenReturn(Optional.of(1));
        when(spiceLevelRepository.getByName(anyString())).thenReturn(Optional.of(1));

        String result = employeeService.updateProfile(profileDto, username);

        assertEquals("Profile has been created successfully", result);
        verify(profileRepository, times(1)).save(any(Profile.class), eq(username));
    }

    private void setField(Object target, String fieldName, Object value) throws NoSuchFieldException, IllegalAccessException {
        Field field = target.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(target, value);
    }
}
