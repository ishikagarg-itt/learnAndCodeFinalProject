package org.example.Services;

import org.example.Dto.FoodItemResponseDto;
import org.example.Handler.OutputHandler;
import org.example.Handler.ProtocolHandler;

import java.io.IOException;
import java.util.List;

import static org.example.Constants.CommandEnum.GET_RECOMMENDATION;

public class RecommendationService {
    public List<FoodItemResponseDto> getRecommendation(String sessionToken, ProtocolHandler protocolHandler) throws IOException {
        protocolHandler.sendRequest(GET_RECOMMENDATION.getCommandName(), "", sessionToken);

        List<FoodItemResponseDto> recommendationResponse = protocolHandler.receiveResponseList(FoodItemResponseDto.class);

        for (FoodItemResponseDto item : recommendationResponse) {
            OutputHandler.printFoodItemResponse(item);
        }

        return recommendationResponse;
    }
}
