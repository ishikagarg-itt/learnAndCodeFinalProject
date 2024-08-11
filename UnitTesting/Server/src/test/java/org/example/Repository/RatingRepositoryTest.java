package org.example.Repository;

import org.example.Constants.DatabaseConstants;
import org.example.Dto.FoodItemRating;
import org.example.Entity.FoodItem;
import org.example.Entity.Rating;
import org.example.Exception.NotFoundException;
import org.example.Mapper.RatingMapper;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class RatingRepositoryTest {

    private RatingRepository ratingRepository;
    private JdbcTemplate jdbcTemplate;

    @Before
    public void setUp() {
        ratingRepository = spy(RatingRepository.class);
        jdbcTemplate = mock(JdbcTemplate.class);

        setField(ratingRepository, "jdbcTemplate", jdbcTemplate);
    }

    @Test
    public void testSaveSuccess() {
        Rating rating = new Rating();
        rating.setRating(String.valueOf(5));
        rating.setComment("Excellent");
        FoodItem foodItem = new FoodItem();
        foodItem.setId(1);
        rating.setFoodItem(foodItem);
        rating.setFeedback("Great");
        rating.setTastePreference("Spicy");
        rating.setRecipe("Recipe details");

        String username = "ishika.garg";

        when(jdbcTemplate.update(eq(DatabaseConstants.INSERT_RATING),
                anyInt(),
                anyString(),
                anyInt(),
                eq(username),
                anyString(),
                anyString(),
                anyString()))
                .thenReturn(1);

        ratingRepository.save(rating, username);

        verify(jdbcTemplate).update(eq(DatabaseConstants.INSERT_RATING),
                eq(rating.getRating()),
                eq(rating.getComment()),
                eq(rating.getFoodItem().getId()),
                eq(username),
                eq(rating.getFeedback()),
                eq(rating.getTastePreference()),
                eq(rating.getRecipe()));
    }


    @Test
    public void testSaveFailure() {
        Rating rating = new Rating();
        rating.setRating(String.valueOf(5));
        rating.setComment("Excellent");
        rating.setFoodItem(new FoodItem());
        rating.setFeedback("Great");
        rating.setTastePreference("Spicy");
        rating.setRecipe("Recipe details");

        String username = "testuser";

        when(jdbcTemplate.update(eq(DatabaseConstants.INSERT_RATING),
                anyInt(),
                anyString(),
                anyInt(),
                eq(username),
                anyString(),
                anyString(),
                anyString()))
                .thenReturn(0);

        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                ratingRepository.save(rating, username)
        );

        assertEquals("Failed to insert rating into the database", exception.getMessage());
    }

    @Test
    public void testGetFoodItemRatingsForTodaySuccess() {
        int foodItemId = 1;
        List<FoodItemRating> expectedRatings = Collections.singletonList(new FoodItemRating());

        when(jdbcTemplate.query(eq(DatabaseConstants.SELECT_RATINGS_FOR_TODAY),
                any(Object[].class),
                any(RatingMapper.class)))
                .thenReturn(expectedRatings);

        List<FoodItemRating> ratings = ratingRepository.getFoodItemRatingsForToday(foodItemId);

        assertNotNull(ratings);
        assertEquals(expectedRatings, ratings);
        verify(jdbcTemplate).query(eq(DatabaseConstants.SELECT_RATINGS_FOR_TODAY),
                any(Object[].class),
                any(RatingMapper.class));
    }

    @Test
    public void testUpdateItemAuditSuccess() {
        FoodItemRating foodItemRating = new FoodItemRating();
        foodItemRating.setAverageRating(4.5);
        foodItemRating.setFoodItemId(1);
        double averageSentiment = 0.8;

        when(jdbcTemplate.update(eq(DatabaseConstants.UPDATE_ITEM_AUDIT),
                anyDouble(),
                anyDouble(),
                anyInt()))
                .thenReturn(1);

        ratingRepository.updateItemAudit(foodItemRating, averageSentiment);

        verify(jdbcTemplate).update(eq(DatabaseConstants.UPDATE_ITEM_AUDIT),
                anyDouble(),
                anyDouble(),
                anyInt());
    }

    @Test
    public void testUpdateItemAuditFailure() {
        FoodItemRating foodItemRating = new FoodItemRating();
        foodItemRating.setAverageRating(4.5);
        foodItemRating.setFoodItemId(1);
        double averageSentiment = 0.8;

        when(jdbcTemplate.update(eq(DatabaseConstants.UPDATE_ITEM_AUDIT),
                anyDouble(),
                anyDouble(),
                anyInt()))
                .thenReturn(0);

        NotFoundException exception = assertThrows(NotFoundException.class, () ->
                ratingRepository.updateItemAudit(foodItemRating, averageSentiment)
        );

        assertEquals("FoodItem not found", exception.getMessage());
    }

    @Test
    public void testUserHasRatedToday() {
        String username = "testuser";
        int foodItemId = 1;

        when(jdbcTemplate.queryForObject(eq(DatabaseConstants.COUNT_RATING_FOR_FOOD_ITEM_BY_USER_TODAY),
                any(Object[].class),
                eq(Integer.class)))
                .thenReturn(1);

        boolean hasRated = ratingRepository.hasUserRatedToday(username, foodItemId);

        assertTrue(hasRated);
        verify(jdbcTemplate).queryForObject(eq(DatabaseConstants.COUNT_RATING_FOR_FOOD_ITEM_BY_USER_TODAY),
                any(Object[].class),
                eq(Integer.class));
    }

    @Test
    public void testUserHasNotRatedToday() {
        String username = "testuser";
        int foodItemId = 1;

        when(jdbcTemplate.queryForObject(eq(DatabaseConstants.COUNT_RATING_FOR_FOOD_ITEM_BY_USER_TODAY),
                any(Object[].class),
                eq(Integer.class)))
                .thenReturn(0);

        boolean hasRated = ratingRepository.hasUserRatedToday(username, foodItemId);

        assertFalse(hasRated);
        verify(jdbcTemplate).queryForObject(eq(DatabaseConstants.COUNT_RATING_FOR_FOOD_ITEM_BY_USER_TODAY),
                any(Object[].class),
                eq(Integer.class));
    }

    private void setField(Object target, String fieldName, Object value) {
        try {
            java.lang.reflect.Field field = target.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(target, value);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
