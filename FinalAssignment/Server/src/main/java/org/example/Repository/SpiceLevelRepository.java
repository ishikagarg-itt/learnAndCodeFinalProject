package org.example.Repository;

import org.example.Config.DataSourceConfig;
import org.example.Config.MySqlDataSourceConfig;
import org.example.Constants.DatabaseConstants;
import org.example.Mapper.FoodItemTypeMapper;
import org.example.Mapper.SpiceLevelMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.Optional;

public class SpiceLevelRepository {
    private final JdbcTemplate jdbcTemplate;

    public SpiceLevelRepository() {
        DataSourceConfig dataSourceConfig = new MySqlDataSourceConfig();
        DataSource dataSource = dataSourceConfig.getDataSource();
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }
    public Optional<Integer> getByName(String spiceLevel){
        int id = jdbcTemplate.queryForObject(DatabaseConstants.SELECT_SPICE_LEVEL_BY_NAME, new Object[]{spiceLevel}, new SpiceLevelMapper());
        return Optional.ofNullable(id);
    }
}
