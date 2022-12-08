package devcourse.baemin.domain.menu;

import devcourse.baemin.domain.value.Amount;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.*;

@Repository
public class JdbcMenuRepository implements MenuRepository {

    private final NamedParameterJdbcTemplate template;
    private final SimpleJdbcInsert jdbcInsert;

    public JdbcMenuRepository(DataSource dataSource) {
        this.template = new NamedParameterJdbcTemplate(dataSource);
        this.jdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("menu");
    }

    @Override
    public void save(Menu menu) {
        SqlParameterSource parameterSource = new BeanPropertySqlParameterSource(menu);
        jdbcInsert.execute(parameterSource);
    }

    @Override
    public List<Menu> findAll() {
        String sql = "select * from menu";

        try {
            return template.query(sql, menuRowMapper());
        } catch (EmptyResultDataAccessException emptyResultDataAccessException) {
            return Collections.emptyList();
        }
    }

    @Override
    public Optional<Menu> findById(UUID menuId) {
        String sql = "select * from menu where menu_id=:menuId";

        try {
            Map<String, String> param = Map.of("menuId", menuId.toString());
            Menu menu = template.queryForObject(sql, param, menuRowMapper());
            return Optional.ofNullable(menu);
        } catch (EmptyResultDataAccessException emptyResultDataAccessException) {
            return Optional.empty();
        }
    }

    @Override
    public void delete(UUID menuId) {
        String sql = "delete from menu where menu_id=:menuId";
        Map<String, String> param = Map.of("menuId", menuId.toString());
        template.update(sql, param);
    }

    private RowMapper<Menu> menuRowMapper() {
        return ((resultSet, rowNum) -> {
            UUID menuId = UUID.fromString(resultSet.getString("menu_id"));
            String menuName = resultSet.getString("menu_name");
            Amount price = new Amount(resultSet.getLong("price"));
            String description = resultSet.getString("description");
            UUID storeId = UUID.fromString(resultSet.getString("store_id"));

            return new Menu(menuId, menuName, price, description, storeId);
        });
    }
}
