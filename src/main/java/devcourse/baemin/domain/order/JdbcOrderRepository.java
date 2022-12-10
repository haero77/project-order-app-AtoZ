package devcourse.baemin.domain.order;

import devcourse.baemin.domain.order.model.Order;
import devcourse.baemin.domain.value.Amount;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;

@Slf4j
@Repository
public class JdbcOrderRepository implements OrderRepository {

    private final NamedParameterJdbcTemplate template;
    private final SimpleJdbcInsert jdbcInsert;

    public JdbcOrderRepository(DataSource dataSource) {
        this.template = new NamedParameterJdbcTemplate(dataSource);
        this.jdbcInsert = (SimpleJdbcInsert) new SimpleJdbcInsert(dataSource)
                .includeSynonymsForTableColumnMetaData()
                .withTableName("orders");
    }

    @Override
    public void save(Order order) {

        SqlParameterSource parameterSource = new BeanPropertySqlParameterSource(order);
        log.debug("help me!!");

//        String[] parameterNames = parameterSource.getParameterNames();
//        for (String parameterName : parameterNames) {
//            log.debug("parameterName = {}, value = {}", parameterName, parameterSource.getValue(parameterName));
//        }
//
        jdbcInsert.execute(parameterSource);
    }

    @Override
    public List<Order> findAll() {
        String sql = "select * from orders";

        try {
            return template.query(sql, orderRowMapper());
        } catch (EmptyResultDataAccessException emptyResultDataAccessException) {
            return Collections.emptyList();
        }
    }

    @Override
    public Optional<Order> findById(UUID orderId) {
        String sql = "select * from orders where order_id=:orderId";

        try {
            Map<String, String> param = Map.of("orderId", orderId.toString());
            Order order = template.queryForObject(sql, param, orderRowMapper());
            return Optional.of(Objects.requireNonNull(order));
        } catch (EmptyResultDataAccessException emptyResultDataAccessException) {
            return Optional.empty();
        }
    }

    @Override
    public void updateCancelledAt(UUID orderId, LocalDateTime cancelledAt) {
        String sql = "update orders " +
                "set cancelled_at=:cancelledAt " +
                "where order_id=:orderId";

        SqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("cancelledAt", cancelledAt)
                .addValue("orderId", orderId.toString());

        template.update(sql, parameterSource);
    }

    private RowMapper<Order> orderRowMapper() {
        return ((resultSet, rowNum) -> {
            UUID orderId = UUID.fromString(resultSet.getString("order_id"));
            String memberId = resultSet.getString("member_id");
            UUID menuId = UUID.fromString(resultSet.getString("menu_id"));
            UUID storeId = UUID.fromString(resultSet.getString("store_id"));
            long orderQuantity = resultSet.getLong("order_quantity");
            Amount orderAmount = new Amount(resultSet.getLong("order_amount"));
            OrderStatus orderStatus = OrderStatus.valueOf(resultSet.getString("order_status"));
            LocalDateTime createdAt = parseToLocalDateTime(resultSet.getTimestamp("created_at"));
            LocalDateTime cancelledAt = parseToLocalDateTime(resultSet.getTimestamp("cancelled_at"));

            return new Order(orderId, memberId, menuId, storeId, orderQuantity, orderAmount, orderStatus, createdAt, cancelledAt);
        });
    }

    private LocalDateTime parseToLocalDateTime(Timestamp timestamp) {
        if (timestamp == null) {
            return null;
        }
        return timestamp.toLocalDateTime();
    }
}
