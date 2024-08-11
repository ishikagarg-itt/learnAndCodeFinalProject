package org.example.Repository;

import org.example.Config.DataSourceConfig;
import org.example.Config.MySqlDataSourceConfig;
import org.example.Constants.DatabaseConstants;
import org.example.Dto.FoodItemRating;
import org.example.Entity.Rating;
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

    public void save(Rating rating, String username){
        int rowsAffected = jdbcTemplate.update(DatabaseConstants.INSERT_RATING,
                rating.getRating(),
                rating.getComment(),
                rating.getFoodItem().getId(),
                username,
                rating.getFeedback(),
                rating.getTastePreference(),
                rating.getRecipe());

        if (rowsAffected == 0) {
            throw new RuntimeException("Failed to insert rating into the database");
        }
    }

    public List<FoodItemRating> getFoodItemRatingsForToday(int foodItemId){
        List<FoodItemRating> foodItemRatings = jdbcTemplate.query(DatabaseConstants.SELECT_RATINGS_FOR_TODAY, new Object[]{foodItemId}, new RatingMapper());
        return foodItemRatings;
    }

    public void updateItemAudit(FoodItemRating foodItemRating, double averageSentiment){
        try {
            int rowsAffected = jdbcTemplate.update(DatabaseConstants.UPDATE_ITEM_AUDIT,
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

    public boolean hasUserRatedToday(String username, int foodItemId) {
        Integer count = jdbcTemplate.queryForObject(DatabaseConstants.COUNT_RATING_FOR_FOOD_ITEM_BY_USER_TODAY, new Object[]{username, foodItemId}, Integer.class);
        return count != null && count > 0;
    }
}
