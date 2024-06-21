package org.example.Repository;

import org.example.Config.DataSourceConfig;
import org.example.Config.MySqlDataSourceConfig;
import org.example.Entity.FoodItem;
import org.example.Exception.NotFoundException;
import org.example.Mapper.FoodItemMapper;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.List;

public class FoodItemRepository implements GenericRepository<FoodItem, Integer> {

    private final JdbcTemplate jdbcTemplate;

    public FoodItemRepository() {
        DataSourceConfig dataSourceConfig = new MySqlDataSourceConfig();
        DataSource dataSource = dataSourceConfig.getDataSource();
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public FoodItem save(FoodItem foodItem) {
        String sql = "INSERT INTO food_item (name, availability_status, type_id) VALUES (?, ?, ?)";
        int rowsAffected = jdbcTemplate.update(sql,
                foodItem.getName(),
                foodItem.isAvailabilityStatus(),
                foodItem.getType().getId());

        if (rowsAffected > 0) {
            return foodItem;
        } else {
            throw new RuntimeException("Failed to insert food item into the database");
        }
    }

    @Override
    public FoodItem getById(Integer id) {
        String sql = "SELECT fi.*,t.name as type_name FROM food_item fi" + "LEFT JOIN food_item_type t ON fi.type_id = t.id" + "WHERE fi.id = ?";
        try {
            FoodItem foodItem = jdbcTemplate.queryForObject(sql, new Object[]{id}, new FoodItemMapper());
            return foodItem;
        } catch (EmptyResultDataAccessException ex) {
            throw new NotFoundException("User not found");
        }
    }

    @Override
    public FoodItem update(Integer id, FoodItem foodItem) {
        String sql = "UPDATE food_item SET Name = ?, IsAvailable = ? WHERE Id = ?";
        try {
            int rowsAffected = jdbcTemplate.update(sql,
                    foodItem.getName(),
                    foodItem.isAvailabilityStatus(),
                    id);

            if (rowsAffected == 0) {
                throw new NotFoundException("FoodItem not found");
            }

            return foodItem;
        } catch (DataAccessException ex) {
            throw new RuntimeException("Database error occurred", ex);
        }
    }

    @Override
    public void delete(Integer id) {
        String sql = "DELETE FROM FoodItems WHERE Id = ?";
        try {
            int rowsAffected = jdbcTemplate.update(sql, id);

            if (rowsAffected == 0) {
                throw new NotFoundException("FoodItem not found");
            }
        } catch (DataAccessException ex) {
            throw new RuntimeException("Database error occurred", ex);
        }
    }

    public List<FoodItem> getTopFoodItems(String foodType){
        String sql = "SELECT fi.*, fit.name as type_name " +
                "FROM item_audit ia " +
                "JOIN food_item fi ON ia.food_item_id = fi.id " +
                "JOIN food_item_type fit ON fi.type_id = fit.id " +
                "WHERE fit.type = ? " +
                "ORDER BY (ia.average_rating + fia.average_sentiment) / 2 DESC " +
                "LIMIT 2";

        return jdbcTemplate.query(sql, new Object[]{foodType}, new FoodItemMapper());
    }
}
