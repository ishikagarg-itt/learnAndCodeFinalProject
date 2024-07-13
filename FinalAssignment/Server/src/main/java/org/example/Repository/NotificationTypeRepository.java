package org.example.Repository;

import org.example.Config.DataSourceConfig;
import org.example.Config.MySqlDataSourceConfig;
import org.example.Constants.DatabaseConstants;
import org.example.Mapper.FoodItemTypeMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.Optional;

public class NotificationTypeRepository {
    private final JdbcTemplate jdbcTemplate;

    public NotificationTypeRepository() {
        DataSourceConfig dataSourceConfig = new MySqlDataSourceConfig();
        DataSource dataSource = dataSourceConfig.getDataSource();
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }
    public Optional<Integer> getByName(String type){
        int id = jdbcTemplate.queryForObject(DatabaseConstants.SELECT_NOTIFICATION_TYPE_BY_NAME, new Object[]{type}, new FoodItemTypeMapper());
        return Optional.ofNullable(id);
    }
}
