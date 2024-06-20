package org.example.Repository;

import org.example.Config.DataSourceConfig;
import org.example.Config.MySqlDataSourceConfig;
import org.example.Entity.User;
import org.example.Exception.NotFoundException;
import org.example.Mapper.UserRowMapper;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.Optional;

public class UserRepository implements GenericRepository<User, Long> {

    private final JdbcTemplate jdbcTemplate;

    public UserRepository() {
        DataSourceConfig dataSourceConfig = new MySqlDataSourceConfig();
        DataSource dataSource = dataSourceConfig.getDataSource();
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Optional<User> findByName(String userName) {
        String sql = "SELECT u.*, r.id as role_id, r.name as role_name " +
                "FROM user u " +
                "LEFT JOIN role r ON u.role_id = r.id " +
                "WHERE u.username = ?";

        User user = jdbcTemplate.queryForObject(sql, new Object[]{userName}, new UserRowMapper());
        return Optional.ofNullable(user);

    }

    @Override
    public User save(User user) {
        return null;
    }

    @Override
    public User findById(Long id) {
        return null;
    }
}
