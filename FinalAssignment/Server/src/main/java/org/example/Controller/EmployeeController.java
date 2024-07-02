package org.example.Controller;

import org.example.Dto.EmployeeMenuDto;
import org.example.Dto.RatingDto;
import org.example.Entity.Notification;
import org.example.Entity.Rating;
import org.example.Services.EmployeeService;

import java.util.List;

public class EmployeeController {
    private final EmployeeService employeeService;

    public EmployeeController(){
        employeeService = new EmployeeService();
    }

    public List<EmployeeMenuDto> getRollOutMenu(){
        List<EmployeeMenuDto> foodItems = employeeService.getRollOutMenu();
        return foodItems;
    }

    public String chooseItems(List<Integer> foodItemIds){
        return employeeService.chooseItems(foodItemIds);
    }

    public String provideRating(RatingDto rating, String username){
        return employeeService.provideRating(rating, username);
    }

    public List<Notification> viewNotifications(){
        return employeeService.viewNotifications();
    }
}
