package org.example.Controller;

import org.example.Dto.LoginRequestDto;
import org.example.Dto.LoginResponseDto;
import org.example.Entity.FoodItem;
import org.example.Services.AuthenticationService;
import org.example.Services.ChefService;

import java.util.List;

public class ChefController {
    private ChefService chefService;

    public ChefController(){
        chefService = new ChefService();
    }

    public String getRecommendation() {
        String foodItems = chefService.getRecommendation();
        return foodItems;
    }
}
