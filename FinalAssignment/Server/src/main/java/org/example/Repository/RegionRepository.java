package org.example.Repository;

import org.example.Config.DataSourceConfig;
import org.example.Config.MySqlDataSourceConfig;
import org.example.Constants.DatabaseConstants;
import org.example.Mapper.FoodItemTypeMapper;
import org.example.Mapper.RegionMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.Optional;

public class RegionRepository {
    private final JdbcTemplate jdbcTemplate;

    public RegionRepository() {
        DataSourceConfig dataSourceConfig = new MySqlDataSourceConfig();
        DataSource dataSource = dataSourceConfig.getDataSource();
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }
    public Optional<Integer> getByName(String region){
        int id = jdbcTemplate.queryForObject(DatabaseConstants.SELECT_REGION_BY_NAME, new Object[]{region}, new RegionMapper());
        return Optional.ofNullable(id);
    }
}
