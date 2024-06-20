package org.example.Repository;

import org.example.Config.DataSourceConfig;
import org.example.Config.MySqlDataSourceConfig;
import org.example.Entity.FoodItemType;
import org.example.Entity.User;
import org.example.Mapper.FoodItemMapper;
import org.example.Mapper.FoodItemTypeMapper;
import org.example.Mapper.UserRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.Optional;

public class FoodItemTypeRepository{
    private final JdbcTemplate jdbcTemplate;

    public FoodItemTypeRepository() {
        DataSourceConfig dataSourceConfig = new MySqlDataSourceConfig();
        DataSource dataSource = dataSourceConfig.getDataSource();
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public Optional<Integer> getByName(String type){
        String sql = "SELECT id from food_item_type " + "WHERE type = ?";

        int id = jdbcTemplate.queryForObject(sql, new Object[]{type}, new FoodItemTypeMapper());
        return Optional.ofNullable(id);
    }
}
