package org.example.Constants;

public class DatabaseConstants {
    public static final String UPDATE_FOOD_ITEM = "UPDATE food_item SET name = ?, availability_status = ?, type_id = ?, preference_id = ?, spice_level_id = ?, region_id = ?, sweet_tooth = ? WHERE id = ?";
    public static final String INSERT_FOOD_ITEM = "INSERT INTO food_item (name, availability_status, type_id, preference_id, spice_level_id, region_id, sweet_tooth) VALUES (?, ?, ?, ?, ?, ?, ?)";
    public static final String SELECT_FOOD_ITEM_BY_ID = "SELECT fi.*, t.type AS type_name, mp.preference AS meal_preference, sl.spice_level AS spice_level, r.region AS region " +
            "FROM food_item fi " +
            "LEFT JOIN food_item_type t ON fi.type_id = t.id " +
            "LEFT JOIN meal_preference mp ON fi.preference_id = mp.id " +
            "LEFT JOIN spice_level sl ON fi.spice_level_id = sl.id " +
            "LEFT JOIN region r ON fi.region_id = r.id " +
            "WHERE fi.id = ?";
    public static final String DELETE_FOOD_ITEM = "DELETE FROM food_item WHERE id = ?";
    public static final String SELECT_ALL_FOOD_ITEMS = "SELECT fi.*, t.type AS type_name, mp.preference AS meal_preference, sl.spice_level AS spice_level, r.region AS region " +
            "FROM food_item fi " +
            "LEFT JOIN food_item_type t ON fi.type_id = t.id " +
            "LEFT JOIN meal_preference mp ON fi.preference_id = mp.id " +
            "LEFT JOIN spice_level sl ON fi.spice_level_id = sl.id " +
            "LEFT JOIN region r ON fi.region_id = r.id ";
    public static final String SELECT_TOP_FOOD_ITEMS = "SELECT fi.*, fit.type AS type_name, mp.preference AS meal_preference, sl.spice_level AS spice_level, r.region AS region, fi.sweet_tooth " +
            "FROM item_audit ia " +
            "JOIN food_item fi ON ia.food_item_id = fi.id " +
            "JOIN food_item_type fit ON fi.type_id = fit.id " +
            "LEFT JOIN meal_preference mp ON fi.preference_id = mp.id " +
            "LEFT JOIN spice_level sl ON fi.spice_level_id = sl.id " +
            "LEFT JOIN region r ON fi.region_id = r.id " +
            "WHERE fit.type = ? " +
            "ORDER BY (ia.average_rating + ia.average_sentiment) / 2 DESC " +
            "LIMIT 5";
    public static final String COUNT_FOOD_ITEM_BY_ID = "SELECT COUNT(*) FROM food_item WHERE id = ?";
    public static final String SELECT_FOOD_TYPE_BY_NAME = "SELECT id from food_item_type " + "WHERE type = ?";
    public static final String INSERT_NOTIFICATION = "INSERT INTO notification(message, notification_type_id) VALUES (?, ?)";
    public static final String SELECT_NOTIFICATIONS_FOR_TODAY = "SELECT n.*,nt.type as type_name FROM notification n "
            + "LEFT JOIN notification_type nt ON n.notification_type_id = nt.id "
            + "WHERE DATE(notification_date) = CURDATE()";
    public static final String SELECT_NOTIFICATION_TYPE_BY_NAME = "SELECT id from notification_type " + "WHERE type = ?";
    public static final String INSERT_RATING = "INSERT INTO rating (rating, comment, food_item_id, username, feedback, taste_preference, recipe) VALUES (?, ?, ?, ?, ?, ?, ?)";
    public static final String SELECT_RATINGS_FOR_TODAY = "SELECT food_item_id, AVG(rating) AS average_rating, GROUP_CONCAT(comment SEPARATOR ', ') AS comments " +
            "FROM rating " +
            "WHERE rating_date = CURDATE() AND food_item_id = ? " +
            "GROUP BY food_item_id";
    public static final String COUNT_RATING_FOR_FOOD_ITEM_BY_USER_TODAY = "SELECT COUNT(*) FROM rating WHERE username = ? AND food_item_id = ? AND DATE(rating_date) = CURDATE()";
    public static final String UPDATE_ITEM_AUDIT = "UPDATE item_audit " +
            "SET average_rating = (average_rating + ?) / 2, " +
            "average_sentiment = (average_sentiment + ?) / 2 " +
            "WHERE food_item_id = ?";
    public static final String SELECT_USER_BY_USERNAME_AND_EMPLOYEE_ID = "SELECT u.*, r.id as role_id, r.name as role_name " +
            "FROM user u " +
            "LEFT JOIN role r ON u.role_id = r.id " +
            "WHERE u.username = ? AND u.employee_id = ?";
    public static final String INSERT_ROLLOUT_MENU_ITEM = "INSERT INTO rollout_menu_item (food_item_id) VALUES (?)";
    public static final String SELECT_MENU_FOR_EMPLOYEE = "SELECT ia.average_rating AS item_rating, ia.average_sentiment AS item_sentiment, " +
            "fit.type AS type_name, fi.name AS item_name, fi.id AS item_id, " +
            "mp.preference AS meal_preference, sl.spice_level AS spice_level, r.region AS region, fi.sweet_tooth " +
            "FROM rollout_menu_item rm " +
            "LEFT JOIN food_item fi ON rm.food_item_id = fi.id " +
            "LEFT JOIN food_item_type fit ON fi.type_id = fit.id " +
            "LEFT JOIN item_audit ia ON ia.food_item_id = fi.id " +
            "LEFT JOIN meal_preference mp ON fi.preference_id = mp.id " +
            "LEFT JOIN spice_level sl ON fi.spice_level_id = sl.id " +
            "LEFT JOIN region r ON fi.region_id = r.id ";
    public static final String SELECT_ITEMS_TO_BE_VOTED_TODAY = "SELECT COUNT(*) FROM rollout_menu_item WHERE food_item_id = ? AND rollout_date = CURRENT_DATE";
    public static final String COUNT_VOTING_FOR_FOOD_ITEM_BY_USER_TODAY = "SELECT COUNT(*) FROM voted_item WHERE username = ? AND food_item_id = ? AND DATE(voting_date) = CURDATE()";
    public static final String INSERT_VOTED_ITEM = "INSERT INTO voted_item (food_item_id, username) VALUES (?, ?)";
    public static final String INSERT_ITEM_AUDIT = "INSERT INTO item_audit (food_item_id, average_rating, average_sentiment) VALUES (?, ?, ?)";
    public static final String SELECT_DISCARD_ITEMS_WITHIN_MONTH = "SELECT fi.name AS food_item_name, fi.availability_status, fit.type AS food_item_type, fi.id AS item_id, " +
            "mp.preference AS meal_preference, sl.spice_level AS spice_level, r.region AS region, fi.sweet_tooth " +
            "FROM discard_item di " +
            "JOIN food_item fi ON di.food_item_id = fi.id " +
            "JOIN food_item_type fit ON fi.type_id = fit.id " +
            "LEFT JOIN meal_preference mp ON fi.preference_id = mp.id " +
            "LEFT JOIN spice_level sl ON fi.spice_level_id = sl.id " +
            "LEFT JOIN region r ON fi.region_id = r.id " +
            "WHERE di.discard_date >= ?";
    public static final String INSERT_DISCARD_ITEM = "INSERT INTO discard_item (food_item_id, discard_date) " +
            "SELECT fi.id, CURRENT_DATE() " +
            "FROM item_audit ia " +
            "JOIN food_item fi ON ia.food_item_id = fi.id " +
            "WHERE (ia.average_rating + ia.average_sentiment) / 2 < 2";
    public static final String SELECT_MEAL_PREFERENCE_TYPE_BY_NAME = "SELECT id from meal_preference " + "WHERE preference = ?";
    public static final String SELECT_SPICE_LEVEL_BY_NAME = "SELECT id from spice_level " + "WHERE spice_level = ?";
    public static final String SELECT_REGION_BY_NAME = "SELECT id from region " + "WHERE region = ?";
    public static final String INSERT_PROFILE = "INSERT INTO profile (meal_preference_id, spice_level_id, region_id, sweet_tooth, username) " +
            "VALUES (?, ?, ?, ?, ?)";
    public static final String GET_USER_PROFILE = "SELECT p.meal_preference_id, p.spice_level_id, p.region_id, p.sweet_tooth " +
            "FROM profile p " +
            "WHERE p.username = ?";
    public static final String SORT_ROLL_OUT_MENU_FOR_PROFILE = "LEFT JOIN profile p ON p.username = ? " +
            "WHERE rm.rollout_date = CURDATE() " +
            "ORDER BY (fi.preference_id = p.meal_preference_id) DESC, " +
            "(fi.spice_level_id = p.spice_level_id) DESC, " +
            "(fi.region_id = p.region_id) DESC, " +
            "(fi.sweet_tooth = p.sweet_tooth) DESC";
    public static final String FILTER_MENU_FOR_CURRENT_DATE = "WHERE rm.rollout_date = CURDATE()";
}
