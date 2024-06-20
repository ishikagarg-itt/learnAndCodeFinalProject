package org.example.Mapper;

import org.example.Entity.FoodItem;
import org.example.Entity.FoodItemType;
import org.example.Entity.Role;
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
        return foodItem;
    }
}
