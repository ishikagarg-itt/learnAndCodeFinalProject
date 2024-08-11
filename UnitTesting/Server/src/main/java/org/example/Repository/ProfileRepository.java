package org.example.Repository;

import org.example.Config.DataSourceConfig;
import org.example.Config.MySqlDataSourceConfig;
import org.example.Constants.DatabaseConstants;
import org.example.Entity.Profile;
import org.example.Entity.Rating;
import org.example.Mapper.ProfileRowMapper;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

import static org.example.Constants.DatabaseConstants.GET_USER_PROFILE;
import static org.example.Constants.DatabaseConstants.INSERT_PROFILE;

public class ProfileRepository {
    private final JdbcTemplate jdbcTemplate;

    public ProfileRepository() {
        DataSourceConfig dataSourceConfig = new MySqlDataSourceConfig();
        DataSource dataSource = dataSourceConfig.getDataSource();
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void save(Profile profile, String username){
        int rowsAffected = jdbcTemplate.update(INSERT_PROFILE,
                profile.getMealPreference().getId(),
                profile.getSpiceLevel().getId(),
                profile.getRegion().getId(),
                profile.isSweetTooth(),
                username);

        if (rowsAffected == 0) {
            throw new RuntimeException("Failed to insert rating into the database");
        }
    }

    public Profile getUserProfile(String username) {
        try {
            return jdbcTemplate.queryForObject(GET_USER_PROFILE, new Object[]{username}, new ProfileRowMapper());
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
    }
}
