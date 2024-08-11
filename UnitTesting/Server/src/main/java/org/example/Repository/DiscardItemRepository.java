package org.example.Repository;

import org.example.Config.DataSourceConfig;
import org.example.Config.MySqlDataSourceConfig;
import org.example.Entity.DiscardItem;
import org.example.Mapper.DiscardItemRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import static org.example.Constants.DatabaseConstants.INSERT_DISCARD_ITEM;
import static org.example.Constants.DatabaseConstants.SELECT_DISCARD_ITEMS_WITHIN_MONTH;

public class DiscardItemRepository {
    private JdbcTemplate jdbcTemplate;

    public DiscardItemRepository() {
        DataSourceConfig dataSourceConfig = new MySqlDataSourceConfig();
        DataSource dataSource = dataSourceConfig.getDataSource();
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public List<DiscardItem> findDiscardItemsWithinOneMonth(LocalDate oneMonthAgo) {

        return jdbcTemplate.query(SELECT_DISCARD_ITEMS_WITHIN_MONTH, new Object[]{Date.valueOf(oneMonthAgo)}, new DiscardItemRowMapper());
    }

    public void insertDiscardItemsFromAudit() {
        jdbcTemplate.update(INSERT_DISCARD_ITEM);
    }
}
