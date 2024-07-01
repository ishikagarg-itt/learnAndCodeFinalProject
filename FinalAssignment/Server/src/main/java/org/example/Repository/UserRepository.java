package org.example.Repository;

import org.example.Config.DataSourceConfig;
import org.example.Config.MySqlDataSourceConfig;
import org.example.Entity.User;
import org.example.Mapper.UserRowMapper;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.Optional;

public class UserRepository implements GenericRepository<User, Integer> {

    private final JdbcTemplate jdbcTemplate;

    public UserRepository() {
        DataSourceConfig dataSourceConfig = new MySqlDataSourceConfig();
        DataSource dataSource = dataSourceConfig.getDataSource();
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public Optional<User> findByUserNameAndEmployeeId(String userName, String employeeId) {
        try {
            String sql = "SELECT u.*, r.id as role_id, r.name as role_name " +
                    "FROM user u " +
                    "LEFT JOIN role r ON u.role_id = r.id " +
                    "WHERE u.username = ? AND u.employee_id = ?";

            User user = jdbcTemplate.queryForObject(sql, new Object[]{userName, employeeId}, new UserRowMapper());
            return Optional.ofNullable(user);
        }catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public User save(User user) {
        return null;
    }

    @Override
    public User getById(Integer id) {
        return null;
    }

    @Override
    public User update(Integer id, User resource) {
        return null;
    }

    @Override
    public void delete(Integer id) {}

}
