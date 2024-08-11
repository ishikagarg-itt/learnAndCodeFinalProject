package org.example.Mapper;

import org.example.Entity.Notification;
import org.example.Entity.NotificationType;
import org.joda.time.DateTime;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;


public class NotificationMapper implements RowMapper<Notification> {
    @Override
    public Notification mapRow(ResultSet rs, int rowNum) throws SQLException {
        Notification notification = new Notification();
        notification.setId(rs.getInt("id"));
        notification.setNotificationDate(new DateTime(rs.getTimestamp("notification_date")));
        notification.setMessage(rs.getString("message"));

        NotificationType notificationType = new NotificationType();
        notificationType.setType(rs.getString("type_name"));
        notification.setNotificationType(notificationType);

        return notification;
    }
}
