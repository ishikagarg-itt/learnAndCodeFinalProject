package org.example.Repository;

import org.example.Constants.DatabaseConstants;
import org.example.Entity.MealPreference;
import org.example.Entity.Profile;
import org.example.Entity.Region;
import org.example.Entity.SpiceLevel;
import org.example.Mapper.ProfileRowMapper;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

public class ProfileRepositoryTest {

    private ProfileRepository profileRepository;
    private JdbcTemplate jdbcTemplate;

    @Before
    public void setUp() {
        profileRepository = spy(ProfileRepository.class);
        jdbcTemplate = mock(JdbcTemplate.class);

        setField(profileRepository, "jdbcTemplate", jdbcTemplate);
    }

    @Test
    public void testSaveSuccess() {
        Profile profile = new Profile();
        profile.setMealPreference(new MealPreference());
        profile.setSpiceLevel(new SpiceLevel());
        profile.setRegion(new Region());
        profile.setSweetTooth(true);

        String username = "testuser";

        when(jdbcTemplate.update(eq(DatabaseConstants.INSERT_PROFILE),
                anyInt(),
                anyInt(),
                anyInt(),
                anyBoolean(),
                eq(username)))
                .thenReturn(1);

        profileRepository.save(profile, username);

        verify(jdbcTemplate).update(eq(DatabaseConstants.INSERT_PROFILE),
                anyInt(),
                anyInt(),
                anyInt(),
                anyBoolean(),
                eq(username));
    }

    @Test
    public void testSaveFailure() {
        Profile profile = new Profile();
        profile.setMealPreference(new MealPreference());
        profile.setSpiceLevel(new SpiceLevel());
        profile.setRegion(new Region());
        profile.setSweetTooth(true);

        String username = "testuser";

        when(jdbcTemplate.update(eq(DatabaseConstants.INSERT_PROFILE),
                anyInt(),
                anyInt(),
                anyInt(),
                anyBoolean(),
                eq(username)))
                .thenReturn(0);

        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                profileRepository.save(profile, username)
        );

        assertEquals("Failed to insert rating into the database", exception.getMessage());
    }

    @Test
    public void testGetUserProfileFound() {
        String username = "testuser";
        Profile expectedProfile = new Profile();

        when(jdbcTemplate.queryForObject(eq(DatabaseConstants.GET_USER_PROFILE),
                any(Object[].class),
                any(ProfileRowMapper.class)))
                .thenReturn(expectedProfile);

        Profile profile = profileRepository.getUserProfile(username);

        assertNotNull(profile);
        assertEquals(expectedProfile, profile);
        verify(jdbcTemplate).queryForObject(eq(DatabaseConstants.GET_USER_PROFILE),
                any(Object[].class),
                any(ProfileRowMapper.class));
    }

    @Test
    public void testGetUserProfileNotFound() {
        String username = "testuser";

        when(jdbcTemplate.queryForObject(eq(DatabaseConstants.GET_USER_PROFILE),
                any(Object[].class),
                any(ProfileRowMapper.class)))
                .thenThrow(EmptyResultDataAccessException.class);

        Profile profile = profileRepository.getUserProfile(username);

        assertNull(profile);
        verify(jdbcTemplate).queryForObject(eq(DatabaseConstants.GET_USER_PROFILE),
                any(Object[].class),
                any(ProfileRowMapper.class));
    }

    private void setField(Object target, String fieldName, Object value) {
        try {
            java.lang.reflect.Field field = target.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(target, value);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
