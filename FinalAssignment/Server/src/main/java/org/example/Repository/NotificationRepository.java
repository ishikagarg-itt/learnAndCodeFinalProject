package org.example.Repository;

import org.example.Config.DataSourceConfig;
import org.example.Config.MySqlDataSourceConfig;
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
        String sql = "INSERT INTO notification(message, notification_type_id) VALUES (?, ?)";
        int rowsAffected = jdbcTemplate.update(sql,
                notification.getMessage(),
                notification.getNotificationType().getId());

        if (rowsAffected > 0) {
            return;
        } else {
            throw new RuntimeException("Failed to insert notification into the database");
        }
    }

    public List<Notification> getNotificationsForCurrentDate() {
        String sql = "SELECT n.*,nt.type as type_name FROM notification n "
                + "LEFT JOIN notification_type nt ON n.notification_type_id = nt.id "
                + "WHERE DATE(notification_date) = CURDATE()";
        try {
            List<Notification> notifications = jdbcTemplate.query(sql, new NotificationMapper());
            return notifications;
        } catch (DataAccessException ex) {
            throw new RuntimeException("Database error occurred", ex);
        }
    }
}
