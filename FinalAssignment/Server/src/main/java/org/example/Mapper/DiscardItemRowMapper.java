package org.example.Mapper;

import org.example.Entity.DiscardItem;
import org.example.Entity.FoodItem;
import org.example.Entity.FoodItemType;
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

        FoodItemType foodItemType = new FoodItemType();
        foodItemType.setType(rs.getString("food_item_type"));
        foodItem.setType(foodItemType);

        discardItem.setFoodItem(foodItem);
        return discardItem;
    }
}
