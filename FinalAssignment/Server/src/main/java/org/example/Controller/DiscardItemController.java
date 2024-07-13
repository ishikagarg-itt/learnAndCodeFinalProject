package org.example.Controller;

import org.example.Entity.DiscardItem;
import org.example.Services.DiscardItemService;

import java.util.List;

public class DiscardItemController {
    private DiscardItemService discardItemService;

    public DiscardItemController(){
        discardItemService = new DiscardItemService();
    }

    public List<DiscardItem> getDiscardItems(){
        return discardItemService.getDiscardItems();
    }
}
