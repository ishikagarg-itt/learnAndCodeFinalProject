package org.example.Controller;

import org.example.Dto.EmployeeMenuDto;
import org.example.Dto.ProfileDto;
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

    public List<EmployeeMenuDto> getRollOutMenu(String username){
        List<EmployeeMenuDto> foodItems = employeeService.getRollOutMenu(username);
        return foodItems;
    }

    public String chooseItems(List<Integer> foodItemIds, String username){
        return employeeService.chooseItems(foodItemIds, username);
    }

    public String provideRating(RatingDto rating, String username){
        return employeeService.provideRating(rating, username);
    }

    public List<Notification> viewNotifications(){
        return employeeService.viewNotifications();
    }

    public String provideDiscardItemRating(RatingDto rating, String username){
        return employeeService.provideDiscardItemRating(rating, username);
    }

    public String updateProfile(ProfileDto profileDto, String username){
        return employeeService.updateProfile(profileDto, username);
    }
}
