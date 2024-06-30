package org.example.Repository;

import org.example.Config.DataSourceConfig;
import org.example.Config.MySqlDataSourceConfig;
import org.example.Entity.FoodItem;
import org.example.Entity.VotedItem;
import org.example.Mapper.FoodItemMapper;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.List;

public class VotedItemRepository {
    private final JdbcTemplate jdbcTemplate;

    public VotedItemRepository() {
        DataSourceConfig dataSourceConfig = new MySqlDataSourceConfig();
        DataSource dataSource = dataSourceConfig.getDataSource();
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public VotedItem save(VotedItem votedItem){
        String sql = "INSERT INTO voted_item (food_item_id, total_votes) VALUES (?, ?)";
        int rowsAffected = jdbcTemplate.update(sql,
                votedItem.getFoodItem().getId(),
                votedItem.getTotalVotes());

        if (rowsAffected > 0) {
            return votedItem;
        } else {
            throw new RuntimeException("Failed to insert voted item into the database");
        }
    }

    public List<FoodItem> getMenuForEmployee() {
        String sql = "SELECT fit.type AS type_name, fi.name AS item_name" +
                "FROM voted_item vi " +
                "LEFT JOIN food_item fi ON vi.food_item_id = fi.id " +
                "LEFT JOIN food_item_type fit ON fi.type_id = fit.id";
        try {
            List<FoodItem> foodItems = jdbcTemplate.query(sql, new FoodItemMapper());
            return foodItems;
        } catch (DataAccessException ex) {
            throw new RuntimeException("Database error occurred", ex);
        }
    }
}
