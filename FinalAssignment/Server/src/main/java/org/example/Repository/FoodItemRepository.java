package org.example.Repository;

import org.example.Config.DataSourceConfig;
import org.example.Config.MySqlDataSourceConfig;
import org.example.Constants.DatabaseConstants;
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
        int rowsAffected = jdbcTemplate.update(DatabaseConstants.INSERT_FOOD_ITEM,
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
        try {
            FoodItem foodItem = jdbcTemplate.queryForObject(DatabaseConstants.SELECT_FOOD_ITEM_BY_ID, new Object[]{id}, new FoodItemMapper());
            return foodItem;
        } catch (EmptyResultDataAccessException ex) {
            throw new NotFoundException("Food item not found");
        }
    }

    @Override
    public FoodItem update(Integer id, FoodItem foodItem) {
        try {
            int rowsAffected = jdbcTemplate.update(DatabaseConstants.UPDATE_FOOD_ITEM,
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
        try {
            int rowsAffected = jdbcTemplate.update(DatabaseConstants.DELETE_FOOD_ITEM, id);

            if (rowsAffected == 0) {
                throw new NotFoundException("FoodItem not found");
            }
        } catch (DataAccessException ex) {
            throw new RuntimeException("Database error occurred");
        }
    }

    public List<FoodItem> getAll() {
        try {
            List<FoodItem> foodItems = jdbcTemplate.query(DatabaseConstants.SELECT_ALL_FOOD_ITEMS, new FoodItemMapper());
            return foodItems;
        } catch (DataAccessException ex) {
            throw new RuntimeException("Database error occurred");
        }
    }

    public Boolean isExist(int id) {

        try {
            Integer count = jdbcTemplate.queryForObject(DatabaseConstants.COUNT_FOOD_ITEM_BY_ID, new Object[]{id}, Integer.class);
            return count != null && count > 0;
        } catch (DataAccessException ex) {
            throw new RuntimeException("Database error occurred", ex);
        }
    }
}
