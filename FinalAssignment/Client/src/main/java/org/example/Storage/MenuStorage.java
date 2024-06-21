package org.example.Storage;

import org.example.Dto.FoodItemResponseDto;

import java.util.ArrayList;
import java.util.List;

public class MenuStorage {
    private static MenuStorage instance;
    private List<FoodItemResponseDto> menu = new ArrayList<>();

    private MenuStorage() {}

    public static synchronized MenuStorage getInstance() {
        if (instance == null) {
            instance = new MenuStorage();
        }
        return instance;
    }

    public List<FoodItemResponseDto> getMenu() {
        return menu;
    }

    public void setMenu(List<FoodItemResponseDto> menu) {
        this.menu = menu;
    }
}

