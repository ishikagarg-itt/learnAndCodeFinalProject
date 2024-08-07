package org.example.Mapper;

import org.example.Dto.EmployeeMenuDto;
import org.example.Entity.FoodItem;
import org.example.Entity.FoodItemType;
import org.example.Entity.MealPreference;
import org.example.Entity.Region;
import org.example.Entity.SpiceLevel;
import org.example.Entity.VotedItem;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class EmployeeMenuMapper implements RowMapper<EmployeeMenuDto> {
    @Override
    public EmployeeMenuDto mapRow(ResultSet rs, int rowNum) throws SQLException {
        EmployeeMenuDto employeeMenu = new EmployeeMenuDto();

        FoodItem foodItem = new FoodItem();
        foodItem.setName(rs.getString("item_name"));
        foodItem.setId(rs.getInt("item_id"));

        FoodItemType foodItemType = new FoodItemType();
        foodItemType.setType(rs.getString("type_name"));
        foodItem.setType(foodItemType);

        MealPreference mealPreference = new MealPreference();
        mealPreference.setPreference(rs.getString("meal_preference"));
        foodItem.setMealPreference(mealPreference);

        SpiceLevel spiceLevel = new SpiceLevel();
        spiceLevel.setSpiceLevel(rs.getString("spice_level"));
        foodItem.setSpiceLevel(spiceLevel);

        Region region = new Region();
        region.setRegion(rs.getString("region"));
        foodItem.setRegion(region);
        foodItem.setSweetTooth(rs.getBoolean("sweet_tooth"));

        employeeMenu.setFoodItem(foodItem);
        employeeMenu.setAverageRating(rs.getDouble("item_rating"));
        double averageSentiment = rs.getDouble("item_sentiment");
        employeeMenu.setComment(getCommentFromSentiment(averageSentiment));
        return employeeMenu;
    }

    private String getCommentFromSentiment(double averageSentiment) {
        int sentimentRange = (int) Math.floor(averageSentiment);

        switch (sentimentRange) {
            case 1:
                return "It is not good";
            case 2:
                return "It could be better";
            case 3:
                return "It is good";
            case 4:
            case 5:
                return "It is excellent";
            default:
                return "Invalid sentiment score";
        }
    }
}
