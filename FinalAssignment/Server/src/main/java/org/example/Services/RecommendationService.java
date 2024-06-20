package org.example.Services;

import org.example.Dto.CommentSentiment;
import org.example.Dto.FoodItemRating;
import org.example.Entity.FoodItem;
import org.example.Repository.RatingRepository;
import org.example.utils.SentimentAnalysis;

import java.util.Arrays;
import java.util.List;

public class RecommendationService {

    private final RatingRepository ratingRepository;

    public RecommendationService(){
        ratingRepository = new RatingRepository();
    }
    public String getRecommendation(){
        List<FoodItemRating> foodItemRatings = ratingRepository.getFoodItemRatingsForToday();

        for (FoodItemRating foodItemRating : foodItemRatings) {
            List<String> comments = Arrays.asList(foodItemRating.getComments().split(", "));

            double averageSentiment = SentimentAnalysis.analyzeSentiments(comments);
            ratingRepository.updateItemAudit(foodItemRating, averageSentiment);
        }

        return "update audit successfully";
    }
}
