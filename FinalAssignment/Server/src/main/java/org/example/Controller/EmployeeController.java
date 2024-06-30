package org.example.Controller;

import org.example.Dto.EmployeeMenuDto;
import org.example.Entity.FoodItem;
import org.example.Entity.VotedItem;
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
}
