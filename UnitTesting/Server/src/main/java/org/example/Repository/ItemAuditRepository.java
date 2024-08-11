package org.example.Repository;

import org.example.Config.DataSourceConfig;
import org.example.Config.MySqlDataSourceConfig;
import org.example.Constants.DatabaseConstants;
import org.example.Entity.FoodItem;
import org.example.Entity.ItemAudit;
import org.example.Mapper.FoodItemMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.List;

public class ItemAuditRepository {
    private final JdbcTemplate jdbcTemplate;

    public ItemAuditRepository() {
        DataSourceConfig dataSourceConfig = new MySqlDataSourceConfig();
        DataSource dataSource = dataSourceConfig.getDataSource();
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }
    public List<FoodItem> getTopFoodItems(String foodType){
        return jdbcTemplate.query(DatabaseConstants.SELECT_TOP_FOOD_ITEMS, new Object[]{foodType}, new FoodItemMapper());
    }

    public void save(ItemAudit itemAudit){
        int rowsAffected = jdbcTemplate.update(DatabaseConstants.INSERT_ITEM_AUDIT,
                itemAudit.getFoodItemId(),
                itemAudit.getAverageRating(),
                itemAudit.getAverageSentiment());

        if (rowsAffected == 0) {
            throw new RuntimeException("Failed to insert item audit into the database");
        }
    }
}
