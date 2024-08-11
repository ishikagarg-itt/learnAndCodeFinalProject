package org.example.Repository;

import org.example.Entity.DiscardItem;
import org.example.Mapper.DiscardItemRowMapper;
import org.example.Repository.DiscardItemRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.jdbc.core.JdbcTemplate;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.example.Constants.DatabaseConstants.INSERT_DISCARD_ITEM;
import static org.example.Constants.DatabaseConstants.SELECT_DISCARD_ITEMS_WITHIN_MONTH;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class DiscardItemRepositoryTest {

    private DiscardItemRepository discardItemRepository;
    private JdbcTemplate jdbcTemplate;

    @Before
    public void setUp() {
        discardItemRepository = spy(new DiscardItemRepository());

        jdbcTemplate = mock(JdbcTemplate.class);

        setField(discardItemRepository, "jdbcTemplate", jdbcTemplate);

        when(jdbcTemplate.query(eq(SELECT_DISCARD_ITEMS_WITHIN_MONTH), ArgumentMatchers.any(Object[].class), any(DiscardItemRowMapper.class)))
                .thenReturn(Arrays.asList(mock(DiscardItem.class), mock(DiscardItem.class)));
    }

    @Test
    public void testFindDiscardItemsWithinOneMonth() {
        LocalDate oneMonthAgo = LocalDate.now().minusMonths(1);
        List<DiscardItem> discardItems = discardItemRepository.findDiscardItemsWithinOneMonth(oneMonthAgo);

        verify(jdbcTemplate).query(eq(SELECT_DISCARD_ITEMS_WITHIN_MONTH), ArgumentMatchers.any(Object[].class), any(DiscardItemRowMapper.class));
        assertEquals(2, discardItems.size());
    }

    @Test
    public void testInsertDiscardItemsFromAudit() {
        discardItemRepository.insertDiscardItemsFromAudit();

        verify(jdbcTemplate).update(INSERT_DISCARD_ITEM);
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
