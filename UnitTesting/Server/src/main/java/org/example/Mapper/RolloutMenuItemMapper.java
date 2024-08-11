package org.example.Mapper;

import org.example.Entity.FoodItem;
import org.example.Entity.RolloutMenuItem;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class RolloutMenuItemMapper implements RowMapper<RolloutMenuItem> {
    @Override
    public RolloutMenuItem mapRow(ResultSet rs, int rowNum) throws SQLException {
        RolloutMenuItem rolloutMenuItem = new RolloutMenuItem();
        rolloutMenuItem.setId(rs.getInt("id"));

        FoodItem foodItem = new FoodItem();
        foodItem.setId(rs.getInt("food_item_id"));
        rolloutMenuItem.setFoodItem(foodItem);

        rolloutMenuItem.setRolloutDate(rs.getDate("rollout_date"));

        return rolloutMenuItem;
    }
}
