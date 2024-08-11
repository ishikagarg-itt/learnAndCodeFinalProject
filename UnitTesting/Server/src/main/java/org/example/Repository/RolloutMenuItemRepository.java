package org.example.Repository;

import org.example.Config.DataSourceConfig;
import org.example.Config.MySqlDataSourceConfig;
import org.example.Constants.DatabaseConstants;
import org.example.Dto.EmployeeMenuDto;
import org.example.Entity.Profile;
import org.example.Entity.RolloutMenuItem;
import org.example.Mapper.EmployeeMenuMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.List;

import static org.example.Constants.DatabaseConstants.FILTER_MENU_FOR_CURRENT_DATE;
import static org.example.Constants.DatabaseConstants.SORT_ROLL_OUT_MENU_FOR_PROFILE;

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
    public Boolean isFoodItemRolledOutToday(int foodItemId) {
        Integer count = jdbcTemplate.queryForObject(
                DatabaseConstants.SELECT_ITEMS_TO_BE_VOTED_TODAY,
                new Object[]{foodItemId},
                Integer.class
        );

        return count != null && count > 0;
    }

    public List<EmployeeMenuDto> getMenuForEmployee(String username, Profile profile) {
        String query = DatabaseConstants.SELECT_MENU_FOR_EMPLOYEE;

        if (profile != null) {
            query += SORT_ROLL_OUT_MENU_FOR_PROFILE;
            return jdbcTemplate.query(query, new Object[]{username}, new EmployeeMenuMapper());
        } else {
            query += FILTER_MENU_FOR_CURRENT_DATE;
            return jdbcTemplate.query(query, new EmployeeMenuMapper());
        }
    }
}
