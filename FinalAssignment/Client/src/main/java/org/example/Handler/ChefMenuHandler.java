package org.example.Handler;

import org.example.Services.RecommendationService;

import java.util.Scanner;

public class ChefMenuHandler implements MenuHandler{

    private RecommendationService recommendationService;

    public ChefMenuHandler(){
        recommendationService = new RecommendationService();
    }
    @Override
    public void showMenu(Scanner scanner) {
        System.out.println("1. Get recommendation");
        System.out.println("2. Logout");
        System.out.print("Choose an option: ");
        int choice = scanner.nextInt();
        while(true){
            switch (choice){
                case 1:

            }
        }
    }
}
