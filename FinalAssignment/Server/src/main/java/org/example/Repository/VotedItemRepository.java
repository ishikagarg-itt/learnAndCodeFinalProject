package org.example.Repository;

import org.example.Config.DataSourceConfig;
import org.example.Config.MySqlDataSourceConfig;
import org.example.Dto.EmployeeMenuDto;
import org.example.Entity.VotedItem;
import org.example.Exception.NotFoundException;
import org.example.Mapper.EmployeeMenuMapper;
import org.example.Mapper.VotedItemMapper;
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

    public VotedItem save(VotedItem votedItem) {
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

    public List<EmployeeMenuDto> getMenuForEmployee() {
        String sql = "SELECT ia.average_rating AS item_rating, ia.average_sentiment AS item_sentiment, fit.type AS type_name, fi.name AS item_name, fi.id AS item_id " +
                "FROM voted_item vi " +
                "LEFT JOIN food_item fi ON vi.food_item_id = fi.id " +
                "LEFT JOIN food_item_type fit ON fi.type_id = fit.id " +
                "LEFT JOIN item_audit ia ON ia.food_item_id = fi.id";
        try {
            List<EmployeeMenuDto> employeeMenuItems = jdbcTemplate.query(sql, new EmployeeMenuMapper());
            return employeeMenuItems;
        } catch (DataAccessException ex) {
            throw new RuntimeException("Database error occurred", ex);
        }
    }

    public void update(VotedItem votedItem) {
        String sql = "UPDATE voted_item SET total_votes = ? WHERE id = ?";
        try {
            int rowsAffected = jdbcTemplate.update(sql,
                    votedItem.getTotalVotes() + 1,
                    votedItem.getId());

            if (rowsAffected == 0) {
                throw new NotFoundException("FoodItem not found");
            }
        } catch (DataAccessException ex) {
            throw new RuntimeException("Database error occurred", ex);
        }
    }

    public VotedItem getItemsToBeVotedForToday(int foodItemId) {
        String selectSql = "SELECT * FROM voted_item WHERE food_item_id = ? AND voting_date = CURRENT_DATE";
        try {
            VotedItem votedItem = jdbcTemplate.queryForObject(selectSql, new Object[]{foodItemId}, (rs, rowNum) -> new VotedItemMapper().mapVotedItemWithoutFoodDetails(rs));
            return votedItem;
        } catch (DataAccessException ex) {
            throw new RuntimeException("Database error occurred", ex);
        }

    }
}
