package org.example.Services;

import org.example.Dto.CommentSentiment;
import org.example.Entity.FoodItem;
import org.example.utils.SentimentAnalysis;

import java.util.List;

public class ChefService {
    private final RecommendationService recommendationService;

    public ChefService(){
        recommendationService = new RecommendationService();
    }
    public String getRecommendation(){
        return recommendationService.getRecommendation();
    }
}
