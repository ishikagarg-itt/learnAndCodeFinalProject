package org.example.Repository;

import org.example.Config.DataSourceConfig;
import org.example.Config.MySqlDataSourceConfig;
import org.example.Constants.DatabaseConstants;
import org.example.Entity.FoodItem;
import org.example.Entity.Notification;
import org.example.Mapper.FoodItemMapper;
import org.example.Mapper.NotificationMapper;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.List;

public class NotificationRepository {
    private final JdbcTemplate jdbcTemplate;

    public NotificationRepository() {
        DataSourceConfig dataSourceConfig = new MySqlDataSourceConfig();
        DataSource dataSource = dataSourceConfig.getDataSource();
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void save(Notification notification) {
        int rowsAffected = jdbcTemplate.update(DatabaseConstants.INSERT_NOTIFICATION,
                notification.getMessage(),
                notification.getNotificationType().getId());

        if (rowsAffected == 0) {
            throw new RuntimeException("Failed to insert notification into the database");
        }
    }

    public List<Notification> getNotificationsForCurrentDate() {
        try {
            List<Notification> notifications = jdbcTemplate.query(DatabaseConstants.SELECT_NOTIFICATIONS_FOR_TODAY, new NotificationMapper());
            return notifications;
        } catch (DataAccessException ex) {
            throw new RuntimeException("Database error occurred", ex);
        }
    }
}
