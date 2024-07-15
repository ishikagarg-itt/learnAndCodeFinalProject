package org.example.Services;

import org.example.Dto.FoodItemRating;
import org.example.Entity.FoodItem;
import org.example.Repository.FoodItemRepository;
import org.example.Repository.ItemAuditRepository;
import org.example.Repository.RatingRepository;
import org.example.utils.SentimentAnalysis;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RecommendationService {

    private final RatingRepository ratingRepository;

    private final FoodItemRepository foodItemRepository;
    private final ItemAuditRepository itemAuditRepository;

    public RecommendationService(){
        ratingRepository = new RatingRepository();
        foodItemRepository = new FoodItemRepository();
        itemAuditRepository = new ItemAuditRepository();
    }
    public List<FoodItem> getRecommendation(){
        List<FoodItem> allTopFoodItems = new ArrayList<>();
        List<FoodItem> breakfastItems = itemAuditRepository.getTopFoodItems("Breakfast");
        List<FoodItem> lunchItems = itemAuditRepository.getTopFoodItems("Lunch");
        List<FoodItem> dinnerItems = itemAuditRepository.getTopFoodItems("Dinner");

        allTopFoodItems.addAll(breakfastItems);
        allTopFoodItems.addAll(lunchItems);
        allTopFoodItems.addAll(dinnerItems);

        return allTopFoodItems;
    }

    public void updateItemAudit(int foodItemId){
        List<FoodItemRating> foodItemRatings = ratingRepository.getFoodItemRatingsForToday(foodItemId);

        for (FoodItemRating foodItemRating : foodItemRatings) {
            List<String> comments = Arrays.asList(foodItemRating.getComments().split(", "));
            double averageSentiment = SentimentAnalysis.analyzeSentiments(comments);
            ratingRepository.updateItemAudit(foodItemRating, averageSentiment);
        }
    }
}
