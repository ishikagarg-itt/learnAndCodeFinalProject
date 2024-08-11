package org.example.Services;

import org.example.Entity.DiscardItem;
import org.example.Repository.DiscardItemRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class DiscardItemServiceTest {

    @Mock
    private DiscardItemRepository discardItemRepository;

    @InjectMocks
    private DiscardItemService discardItemService;

    private List<DiscardItem> sampleDiscardItems;

    @Before
    public void setUp() {
        sampleDiscardItems = Arrays.asList(new DiscardItem(), new DiscardItem());
    }

    @Test
    public void testGetDiscardItemsWithItemsWithinLastMonth() {
        when(discardItemRepository.findDiscardItemsWithinOneMonth(any(LocalDate.class))).thenReturn(sampleDiscardItems);
        List<DiscardItem> result = discardItemService.getDiscardItems();

        assertEquals(sampleDiscardItems.size(), result.size());
        verify(discardItemRepository, times(1)).findDiscardItemsWithinOneMonth(any(LocalDate.class));
        verify(discardItemRepository, never()).insertDiscardItemsFromAudit();
    }

    @Test
    public void testGetDiscardItemsNoItemsWithinLastMonth() {
        when(discardItemRepository.findDiscardItemsWithinOneMonth(any(LocalDate.class)))
                .thenReturn(Collections.emptyList())
                .thenReturn(sampleDiscardItems);

        List<DiscardItem> result = discardItemService.getDiscardItems();

        assertEquals(sampleDiscardItems.size(), result.size());
        verify(discardItemRepository, times(2)).findDiscardItemsWithinOneMonth(any(LocalDate.class));
        verify(discardItemRepository, times(1)).insertDiscardItemsFromAudit();
    }

    @Test
    public void testGetDiscardItemsWithinLastMonth() {
        LocalDate oneMonthAgo = LocalDate.now().minusMonths(1);
        when(discardItemRepository.findDiscardItemsWithinOneMonth(oneMonthAgo)).thenReturn(sampleDiscardItems);

        List<DiscardItem> result = discardItemService.getDiscardItemsWithinLastMonth();

        assertEquals(sampleDiscardItems.size(), result.size());
        verify(discardItemRepository, times(1)).findDiscardItemsWithinOneMonth(oneMonthAgo);
    }

    @Test
    public void testInsertDiscardItemsFromAudit() {
        discardItemService.insertDiscardItemsFromAudit();
        verify(discardItemRepository, times(1)).insertDiscardItemsFromAudit();
    }
}
