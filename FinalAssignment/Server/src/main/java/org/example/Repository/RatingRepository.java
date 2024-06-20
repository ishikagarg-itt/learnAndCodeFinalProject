package org.example.Repository;

import org.example.Config.DataSourceConfig;
import org.example.Config.MySqlDataSourceConfig;
import org.example.Dto.FoodItemRating;
import org.example.Exception.NotFoundException;
import org.example.Mapper.RatingMapper;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.List;

public class RatingRepository {

    private final JdbcTemplate jdbcTemplate;

    public RatingRepository() {
        DataSourceConfig dataSourceConfig = new MySqlDataSourceConfig();
        DataSource dataSource = dataSourceConfig.getDataSource();
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public List<FoodItemRating> getFoodItemRatingsForToday(){
        String sql = "SELECT food_item_id, AVG(rating) AS average_rating, GROUP_CONCAT(comment SEPARATOR ', ') AS comments " +
                "FROM rating " +
                "WHERE rating_date = CURDATE() " +
                "GROUP BY food_item_id";

        List<FoodItemRating> foodItemRatings = jdbcTemplate.query(sql, new RatingMapper());
        return foodItemRatings;
    }

    public void updateItemAudit(FoodItemRating foodItemRating, double averageSentiment){
        String sql = "UPDATE item_audit SET average_rating = ?, average_sentiment = ? WHERE food_item_id = ?";
        try {
            int rowsAffected = jdbcTemplate.update(sql,
                    foodItemRating.getAverageRating(),
                    averageSentiment,
                    foodItemRating.getFoodItemId());

            if (rowsAffected == 0) {
                throw new NotFoundException("FoodItem not found");
            }
        } catch (DataAccessException ex) {
            throw new RuntimeException("Database error occurred", ex);
        }
    }
}
