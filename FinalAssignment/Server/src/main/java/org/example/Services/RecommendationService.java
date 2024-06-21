package org.example.Services;

import org.example.Dto.CommentSentiment;
import org.example.Dto.FoodItemRating;
import org.example.Entity.FoodItem;
import org.example.Repository.FoodItemRepository;
import org.example.Repository.RatingRepository;
import org.example.utils.SentimentAnalysis;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RecommendationService {

    private final RatingRepository ratingRepository;

    private final FoodItemRepository foodItemRepository;

    public RecommendationService(){
        ratingRepository = new RatingRepository();
        foodItemRepository = new FoodItemRepository();
    }
    public List<FoodItem> getRecommendation(){
        List<FoodItem> allTopFoodItems = new ArrayList<>();
        List<FoodItemRating> foodItemRatings = ratingRepository.getFoodItemRatingsForToday();

        for (FoodItemRating foodItemRating : foodItemRatings) {
            System.out.println("foodItemRating:" + foodItemRating.getAverageRating());
            List<String> comments = Arrays.asList(foodItemRating.getComments().split(", "));

            double averageSentiment = SentimentAnalysis.analyzeSentiments(comments);
            ratingRepository.updateItemAudit(foodItemRating, averageSentiment);
        }

        List<FoodItem> breakfastItems = foodItemRepository.getTopFoodItems("Breakfast");
        List<FoodItem> lunchItems = foodItemRepository.getTopFoodItems("Lunch");
        List<FoodItem> dinnerItems = foodItemRepository.getTopFoodItems("Dinner");

        allTopFoodItems.addAll(breakfastItems);
        allTopFoodItems.addAll(lunchItems);
        allTopFoodItems.addAll(dinnerItems);

        System.out.println("allTopFoodItems" + allTopFoodItems.size());

        return allTopFoodItems;
    }
}
