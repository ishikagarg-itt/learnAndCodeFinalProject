package org.example.Repository;

import org.example.Entity.FoodItem;
import org.example.Entity.ItemAudit;
import org.example.Mapper.FoodItemMapper;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.jdbc.core.JdbcTemplate;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

import static org.example.Constants.DatabaseConstants.INSERT_ITEM_AUDIT;
import static org.example.Constants.DatabaseConstants.SELECT_TOP_FOOD_ITEMS;
import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class ItemAuditRepositoryTest {

    private ItemAuditRepository itemAuditRepository;
    private JdbcTemplate jdbcTemplate;

    @Before
    public void setUp() {
        itemAuditRepository = spy(ItemAuditRepository.class);

        jdbcTemplate = mock(JdbcTemplate.class);
        setField(itemAuditRepository, "jdbcTemplate", jdbcTemplate);
    }

    @Test
    public void testGetTopFoodItems() {
        List<FoodItem> mockFoodItems = Arrays.asList(mock(FoodItem.class), mock(FoodItem.class));

        when(jdbcTemplate.query(eq(SELECT_TOP_FOOD_ITEMS), any(Object[].class), any(FoodItemMapper.class)))
                .thenReturn(mockFoodItems);

        List<FoodItem> result = itemAuditRepository.getTopFoodItems("Vegan");

        assertEquals(mockFoodItems, result);
        verify(jdbcTemplate).query(eq(SELECT_TOP_FOOD_ITEMS), any(Object[].class), any(FoodItemMapper.class));
    }

    @Test
    public void testSaveSuccess() {
        ItemAudit itemAudit = new ItemAudit();
        itemAudit.setFoodItemId(1);
        itemAudit.setAverageRating(4.5);
        itemAudit.setAverageSentiment(0.75);

        when(jdbcTemplate.update(INSERT_ITEM_AUDIT, itemAudit.getFoodItemId(), itemAudit.getAverageRating(), itemAudit.getAverageSentiment()))
                .thenReturn(1);

        itemAuditRepository.save(itemAudit);

        verify(jdbcTemplate).update(INSERT_ITEM_AUDIT, itemAudit.getFoodItemId(), itemAudit.getAverageRating(), itemAudit.getAverageSentiment());
    }

    @Test
    public void testSaveFailure() {
        ItemAudit itemAudit = new ItemAudit();
        itemAudit.setFoodItemId(1);
        itemAudit.setAverageRating(4.5);
        itemAudit.setAverageSentiment(0.75);

        when(jdbcTemplate.update(INSERT_ITEM_AUDIT, itemAudit.getFoodItemId(), itemAudit.getAverageRating(), itemAudit.getAverageSentiment()))
                .thenReturn(0);

        assertThrows(RuntimeException.class, () -> itemAuditRepository.save(itemAudit));

        verify(jdbcTemplate).update(INSERT_ITEM_AUDIT, itemAudit.getFoodItemId(), itemAudit.getAverageRating(), itemAudit.getAverageSentiment());
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
