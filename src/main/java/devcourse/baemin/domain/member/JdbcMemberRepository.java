package devcourse.baemin.domain.member;

import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@Slf4j
public class JdbcMemberRepository implements MemberRepository {

    private final NamedParameterJdbcTemplate template;
    private final SimpleJdbcInsert jdbcInsert;

    public JdbcMemberRepository(DataSource dataSource) {
        this.template = new NamedParameterJdbcTemplate(dataSource);
        this.jdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("member")
                .usingColumns("member_id", "password", "member_name");
    }

    @Override
    public void save(Member member) throws DataIntegrityViolationException {
        SqlParameterSource parameterSource = new BeanPropertySqlParameterSource(member);
        jdbcInsert.execute(parameterSource);
    }

    @Override
    public List<Member> findAll() {
        String sql = "select * from member";

        try {
            return template.query(sql, memberRowMapper());
        } catch (EmptyResultDataAccessException emptyResultDataAccessException) {
            return Collections.emptyList();
        }
    }

    @Override
    public Optional<Member> findById(String memberId) {
        String sql = "select * from member " +
                "where member_id=:memberId";

        try {
            Map<String, String> param = Map.of("memberId", memberId);
            Member member = template.queryForObject(sql, param, memberRowMapper());
            return Optional.ofNullable(member);
        } catch (EmptyResultDataAccessException emptyResultDataAccessException) {
            return Optional.empty();
        }
    }

    private RowMapper<Member> memberRowMapper() {
        return ((resultSet, rowNum) -> {
            String memberId = resultSet.getString("member_id");
            String password = resultSet.getString("password");
            String memberName = resultSet.getString("member_name");
            return new Member(memberId, password, memberName);
        });
    }
}
