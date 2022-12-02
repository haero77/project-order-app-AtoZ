package devcourse.baemin.repository;

import devcourse.baemin.domain.Store;
import devcourse.baemin.domain.value.Amount;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.util.Collections;
import java.util.List;

@Repository
public class JdbcStoreRepository implements StoreRepository{

    private final NamedParameterJdbcTemplate template;
    private final SimpleJdbcInsert jdbcInsert;

    public JdbcStoreRepository(DataSource dataSource) {
        this.template = new NamedParameterJdbcTemplate(dataSource);
        this.jdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("store")
                .usingColumns("store_name", "minimum_order_amount");
    }

    @Override
    public void save(Store store) {
        SqlParameterSource parameterSource = new BeanPropertySqlParameterSource(store);
        jdbcInsert.execute(parameterSource);
    }

    @Override
    public List<Store> findAll() {
        String sql = "select * from store";

        try {
            return template.query(sql, storeRowMapper());
        } catch (DataAccessException dataAccessException) {
            return Collections.emptyList();
        }
    }

    private RowMapper<Store> storeRowMapper() {
        return ((resultSet, rowNum) -> {
            String storeName = resultSet.getString("store_name");
            Amount minimumOrderAmount = new Amount(resultSet.getLong("minimum_order_amount"));
            return new Store(storeName, minimumOrderAmount);
        });
    }
}
