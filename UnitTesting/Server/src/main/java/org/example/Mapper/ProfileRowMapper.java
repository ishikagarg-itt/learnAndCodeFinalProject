package org.example.Mapper;

import org.example.Entity.MealPreference;
import org.example.Entity.Profile;
import org.example.Entity.Region;
import org.example.Entity.SpiceLevel;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ProfileRowMapper implements RowMapper<Profile> {
    @Override
    public Profile mapRow(ResultSet rs, int rowNum) throws SQLException {
        Profile profile = new Profile();

        MealPreference mealPreference = new MealPreference();
        mealPreference.setId(rs.getInt("meal_preference_id"));
        profile.setMealPreference(mealPreference);

        SpiceLevel spiceLevel = new SpiceLevel();
        spiceLevel.setId(rs.getInt("spice_level_id"));
        profile.setSpiceLevel(spiceLevel);

        Region region = new Region();
        region.setId(rs.getInt("region_id"));
        profile.setRegion(region);

        profile.setSweetTooth(rs.getBoolean("sweet_tooth"));

        return profile;
    }
}
