package org.example.Mapper;

import org.example.Entity.FoodItem;
import org.example.Entity.FoodItemType;
import org.example.Entity.MealPreference;
import org.example.Entity.Region;
import org.example.Entity.Role;
import org.example.Entity.SpiceLevel;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class FoodItemMapper implements RowMapper<FoodItem> {
    @Override
    public FoodItem mapRow(ResultSet rs, int rowNum) throws SQLException {
        FoodItem foodItem = new FoodItem();
        foodItem.setId(rs.getInt("id"));
        foodItem.setName(rs.getString("name"));
        foodItem.setAvailabilityStatus(rs.getBoolean("availability_status"));

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
        return foodItem;
    }
}
