package org.example.Repository;

import org.example.Config.DataSourceConfig;
import org.example.Config.MySqlDataSourceConfig;
import org.example.Entity.FoodItem;
import org.example.Entity.Notification;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

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
}
