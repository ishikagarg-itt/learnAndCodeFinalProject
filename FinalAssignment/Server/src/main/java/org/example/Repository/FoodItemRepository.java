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

public class FoodItemRepository implements GenericRepository<FoodItem, Integer> {

    private final JdbcTemplate jdbcTemplate;

    public FoodItemRepository() {
        DataSourceConfig dataSourceConfig = new MySqlDataSourceConfig();
        DataSource dataSource = dataSourceConfig.getDataSource();
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public FoodItem save(FoodItem foodItem) {
        String sql = "INSERT INTO food_item (name, availability, type_id) VALUES (?, ?, ?)";
        int rowsAffected = jdbcTemplate.update(sql,
                foodItem.getName(),
                foodItem.isAvailabilityStatus(),
                foodItem.getType());

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
}
