package org.example.Mapper;

import org.example.Dto.FoodItemRating;
import org.springframework.jdbc.core.RowMapper;

import javax.swing.tree.TreePath;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RatingMapper implements RowMapper<FoodItemRating> {
    @Override
    public FoodItemRating mapRow(ResultSet rs, int rowNum) throws SQLException {
        FoodItemRating foodItemRating = new FoodItemRating();
        foodItemRating.setFoodItemId(rs.getInt("food_item_id"));
        foodItemRating.setAverageRating(rs.getDouble("average_rating"));
        foodItemRating.setComments(rs.getString("comments"));
        return foodItemRating;
    }
}
