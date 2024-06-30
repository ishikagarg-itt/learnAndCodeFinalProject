package org.example.Mapper;


import org.example.Entity.FoodItem;
import org.example.Entity.FoodItemType;
import org.example.Entity.Role;
import org.example.Entity.User;
import org.example.Entity.VotedItem;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class VotedItemMapper implements RowMapper<VotedItem> {
    @Override
    public VotedItem mapRow(ResultSet rs, int rowNum) throws SQLException {
        VotedItem votedItem = new VotedItem();

        FoodItem foodItem = new FoodItem();
        foodItem.setName(rs.getString("item_name"));

        FoodItemType foodItemType = new FoodItemType();
        foodItemType.setType(rs.getString("type_name"));
        foodItem.setType(foodItemType);

        votedItem.setFoodItem(foodItem);
        return votedItem;
    }
}
