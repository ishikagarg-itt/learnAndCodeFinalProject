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
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

import static org.example.Constants.DatabaseConstants.INSERT_FOOD_ITEM;

public class FoodItemRepository implements GenericRepository<FoodItem, Integer> {

    private final JdbcTemplate jdbcTemplate;

    public FoodItemRepository() {
        DataSourceConfig dataSourceConfig = new MySqlDataSourceConfig();
        DataSource dataSource = dataSourceConfig.getDataSource();
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public FoodItem save(FoodItem foodItem) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        int rowsAffected = jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(INSERT_FOOD_ITEM, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, foodItem.getName());
            ps.setBoolean(2, foodItem.isAvailabilityStatus());
            ps.setInt(3, foodItem.getType().getId());
            ps.setInt(4, foodItem.getMealPreference().getId());
            ps.setInt(5, foodItem.getSpiceLevel().getId());
            ps.setInt(6, foodItem.getRegion().getId());
            ps.setBoolean(7, foodItem.isSweetTooth());
            return ps;
        }, keyHolder);
        if (rowsAffected > 0) {
            int generatedId = keyHolder.getKey().intValue();
            System.out.println("generatedId" + generatedId);
            foodItem.setId(generatedId);
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
                    foodItem.getType().getId(),
                    foodItem.getMealPreference().getId(),
                    foodItem.getSpiceLevel().getId(),
                    foodItem.getRegion().getId(),
                    foodItem.isSweetTooth(),
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
