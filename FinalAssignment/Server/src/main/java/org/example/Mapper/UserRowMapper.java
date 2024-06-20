package org.example.Mapper;

import org.example.Entity.Role;
import org.example.Entity.User;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRowMapper implements RowMapper<User> {

    @Override
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
        User user = new User();
        user.setEmployeeId(rs.getString("employee_id"));
        user.setId(rs.getLong("id"));
        user.setName(rs.getString("name"));
        user.setEmail(rs.getString("email"));
        user.setPhoneNumber(rs.getString("phone_number"));
        user.setUserName(rs.getString("username"));

        Role role = new Role();
        role.setId(rs.getLong("role_id"));
        role.setName(rs.getString("role_name"));
        user.setRole(role);
        return user;
    }
}
