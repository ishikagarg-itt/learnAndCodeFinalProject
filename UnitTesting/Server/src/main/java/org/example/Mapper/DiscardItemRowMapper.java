package org.example.Mapper;

import org.example.Entity.DiscardItem;
import org.example.Entity.FoodItem;
import org.example.Entity.FoodItemType;
import org.example.Entity.MealPreference;
import org.example.Entity.Region;
import org.example.Entity.SpiceLevel;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DiscardItemRowMapper implements RowMapper<DiscardItem> {
    @Override
    public DiscardItem mapRow(ResultSet rs, int rowNum) throws SQLException {
        DiscardItem discardItem = new DiscardItem();

        FoodItem foodItem = new FoodItem();
        foodItem.setName(rs.getString("food_item_name"));
        foodItem.setAvailabilityStatus(rs.getBoolean("availability_status"));
        foodItem.setId(rs.getInt("item_id"));

        FoodItemType foodItemType = new FoodItemType();
        foodItemType.setType(rs.getString("food_item_type"));
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


        discardItem.setFoodItem(foodItem);
        return discardItem;
    }
}
