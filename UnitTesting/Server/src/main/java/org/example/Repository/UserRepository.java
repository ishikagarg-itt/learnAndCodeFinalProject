package org.example.Repository;

import org.example.Config.DataSourceConfig;
import org.example.Config.MySqlDataSourceConfig;
import org.example.Constants.DatabaseConstants;
import org.example.Entity.User;
import org.example.Mapper.UserRowMapper;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.Optional;

public class UserRepository {

    private final JdbcTemplate jdbcTemplate;

    public UserRepository() {
        DataSourceConfig dataSourceConfig = new MySqlDataSourceConfig();
        DataSource dataSource = dataSourceConfig.getDataSource();
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public Optional<User> findByUserNameAndEmployeeId(String userName, String employeeId) {
        try {
            User user = jdbcTemplate.queryForObject(DatabaseConstants.SELECT_USER_BY_USERNAME_AND_EMPLOYEE_ID, new Object[]{userName, employeeId}, new UserRowMapper());
            return Optional.ofNullable(user);
        }catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }
}
