package org.example.Services;

import org.example.Entity.DiscardItem;
import org.example.Repository.DiscardItemRepository;

import java.time.LocalDate;
import java.util.List;

public class DiscardItemService {
    private DiscardItemRepository discardItemRepository;

    public DiscardItemService() {
        this.discardItemRepository = new DiscardItemRepository();
    }

    public List<DiscardItem> getDiscardItems() {
        List<DiscardItem> discardItems = getDiscardItemsWithinLastMonth();

        if (!discardItems.isEmpty()) {
            return discardItems;
        } else {
            insertDiscardItemsFromAudit();
            return getDiscardItemsWithinLastMonth();
        }
    }

    public List<DiscardItem> getDiscardItemsWithinLastMonth() {
        LocalDate oneMonthAgo = LocalDate.now().minusMonths(1);
        return discardItemRepository.findDiscardItemsWithinOneMonth(oneMonthAgo);
    }

    public void insertDiscardItemsFromAudit() {
        discardItemRepository.insertDiscardItemsFromAudit();
    }
}
