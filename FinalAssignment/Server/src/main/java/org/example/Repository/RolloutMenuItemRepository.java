package org.example.Repository;

import org.example.Config.DataSourceConfig;
import org.example.Config.MySqlDataSourceConfig;
import org.example.Constants.DatabaseConstants;
import org.example.Dto.EmployeeMenuDto;
import org.example.Entity.Profile;
import org.example.Entity.RolloutMenuItem;
import org.example.Mapper.EmployeeMenuMapper;
import org.example.Mapper.RolloutMenuItemMapper;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.List;

public class RolloutMenuItemRepository {

    private final JdbcTemplate jdbcTemplate;

    public RolloutMenuItemRepository() {
        DataSourceConfig dataSourceConfig = new MySqlDataSourceConfig();
        DataSource dataSource = dataSourceConfig.getDataSource();
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public RolloutMenuItem save(RolloutMenuItem rolloutMenuItem) {
        int rowsAffected = jdbcTemplate.update(DatabaseConstants.INSERT_ROLLOUT_MENU_ITEM,
                rolloutMenuItem.getFoodItem().getId());

        if (rowsAffected > 0) {
            return rolloutMenuItem;
        } else {
            throw new RuntimeException("Failed to insert voted item into the database");
        }
    }
    public RolloutMenuItem getItemsToBeVotedForToday(int foodItemId) {
        try {
            RolloutMenuItem rolloutMenuItem = jdbcTemplate.queryForObject(DatabaseConstants.SELECT_ITEMS_TO_BE_VOTED_TODAY, new Object[]{foodItemId}, new RolloutMenuItemMapper());
            return rolloutMenuItem;
        } catch (DataAccessException ex) {
            throw new RuntimeException("Database error occurred", ex);
        }

    }

    public List<EmployeeMenuDto> getMenuForEmployee(String username, Profile profile) {
        String query = DatabaseConstants.SELECT_MENU_FOR_EMPLOYEE;

        if (profile != null) {
            query += " LEFT JOIN profile p ON p.username = ? " +
                    "ORDER BY CASE WHEN p.username IS NOT NULL THEN 0 ELSE 1 END, " +
                    "p.meal_preference_id, p.spice_level_id, p.region_id, p.sweet_tooth DESC";
            return jdbcTemplate.query(query, new Object[]{username}, new EmployeeMenuMapper());
        } else {
            return jdbcTemplate.query(query, new EmployeeMenuMapper());
        }
    }
}
