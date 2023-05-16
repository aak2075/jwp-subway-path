package subway.persistance;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import subway.domain.Line;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class LineDao {
    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert insertAction;

    private final RowMapper<Line> rowMapper = (rs, rowNum) ->
            new Line(
                    rs.getLong("line_id"),
                    rs.getString("name"),
                    rs.getString("color")
            );

    public LineDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.insertAction = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("line")
                .usingGeneratedKeyColumns("line_id");
    }

    public Line insert(Line line) {
        Map<String, Object> params = new HashMap<>();
        params.put("line_id", line.getId());
        params.put("name", line.getName());
        params.put("color", line.getColor());

        Long lineId = insertAction.executeAndReturnKey(params).longValue();
        return new Line(lineId, line.getName(), line.getColor());
    }

    public List<Line> findAll() {
        String sql = "select line_id, name, color from LINE";
        return jdbcTemplate.query(sql, rowMapper);
    }

    public Optional<Line> findById(Long id) {
        String sql = "select line_id, name, color from LINE WHERE line_id = ?";
        return Optional.ofNullable(jdbcTemplate.queryForObject(sql, rowMapper, id));
    }

    public void deleteById(Long id) {
        jdbcTemplate.update("delete from Line where line_id = ?", id);
    }
}