package org.example.Repository;

import org.example.Config.DataSourceConfig;
import org.example.Config.MySqlDataSourceConfig;
import org.example.Constants.DatabaseConstants;
import org.example.Entity.VotedItem;
import org.example.Exception.NotFoundException;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

public class VotedItemRepository {
    private final JdbcTemplate jdbcTemplate;

    public VotedItemRepository() {
        DataSourceConfig dataSourceConfig = new MySqlDataSourceConfig();
        DataSource dataSource = dataSourceConfig.getDataSource();
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void save(VotedItem votedItem) {
        int rowsAffected = jdbcTemplate.update(DatabaseConstants.INSERT_VOTED_ITEM,
                votedItem.getFoodItem().getId(),
                votedItem.getUser().getUserName());

        if (rowsAffected == 0) {
            throw new RuntimeException("Failed to insert voted item into the database");
        }
    }

    public boolean hasUserVotedForFoodItemToday(String username, int foodItemId) {
        Integer count = jdbcTemplate.queryForObject(DatabaseConstants.COUNT_VOTING_FOR_FOOD_ITEM_BY_USER_TODAY, new Object[]{username, foodItemId}, Integer.class);
        return count != null && count > 0;
    }
}
