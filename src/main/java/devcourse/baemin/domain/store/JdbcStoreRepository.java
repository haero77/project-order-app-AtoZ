package devcourse.baemin.domain.store;

import devcourse.baemin.global.value.Amount;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.*;

@Slf4j
@Repository
public class JdbcStoreRepository implements StoreRepository {

    private final NamedParameterJdbcTemplate template;
    private final SimpleJdbcInsert jdbcInsert;

    public JdbcStoreRepository(DataSource dataSource) {
        this.template = new NamedParameterJdbcTemplate(dataSource);
        this.jdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("store")
                .usingColumns("store_id", "store_name", "minimum_order_amount");
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

    @Override
    public Optional<Store> findById(UUID storeId) {
        String sql = "select * from store " +
                "where store_id=:storeId";

        try {
            Map<String, String> param = Map.of("storeId", storeId.toString());
            Store store = template.queryForObject(sql, param, storeRowMapper());
            return Optional.of(Objects.requireNonNull(store));
        } catch (EmptyResultDataAccessException emptyResultDataAccessException) {
            return Optional.empty();
        }
    }

    @Override
    public void updateMinimumOrderAmount(UUID storeId, Amount minimumOrderAmount) {
        log.info("Update minimumOrderAmount of store '{}' to amount '{}'", storeId, minimumOrderAmount.getAmount());

        String sql = "update store " +
                "set minimum_order_amount=:minimum_order_amount " +
                "where store_id=:store_id";

        SqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("minimum_order_amount", minimumOrderAmount.getAmount())
                .addValue("store_id", storeId.toString());

        template.update(sql, parameterSource);
    }


    private RowMapper<Store> storeRowMapper() {
        return ((resultSet, rowNum) -> {
            UUID storeId = UUID.fromString(resultSet.getString("store_id"));
            String storeName = resultSet.getString("store_name");
            Amount minimumOrderAmount = new Amount(resultSet.getLong("minimum_order_amount"));
            return new Store(storeId, storeName, minimumOrderAmount);
        });
    }
}
