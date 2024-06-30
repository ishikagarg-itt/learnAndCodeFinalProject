package org.example.Mapper;


import org.example.Entity.FoodItem;
import org.example.Entity.FoodItemType;
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

    public VotedItem mapVotedItemWithoutFoodDetails(ResultSet rs) throws SQLException {
        VotedItem votedItem = new VotedItem();
        votedItem.setId(rs.getInt("id"));
        FoodItem foodItem = new FoodItem();
        foodItem.setId(rs.getInt("food_item_id"));
        votedItem.setFoodItem(foodItem);
        votedItem.setVotingDate(rs.getDate("voting_date"));
        votedItem.setTotalVotes(rs.getInt("total_votes"));
        return votedItem;
    }
}
