package org.example.Repository;

import org.example.Config.DataSourceConfig;
import org.example.Config.MySqlDataSourceConfig;
import org.example.Constants.DatabaseConstants;
import org.example.Mapper.MealPreferenceMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.Optional;

public class MealPreferenceRepository {
    private final JdbcTemplate jdbcTemplate;

    public MealPreferenceRepository() {
        DataSourceConfig dataSourceConfig = new MySqlDataSourceConfig();
        DataSource dataSource = dataSourceConfig.getDataSource();
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }
    public Optional<Integer> getByName(String type){
        int id = jdbcTemplate.queryForObject(DatabaseConstants.SELECT_MEAL_PREFERENCE_TYPE_BY_NAME, new Object[]{type}, new MealPreferenceMapper());
        return Optional.ofNullable(id);
    }
}
