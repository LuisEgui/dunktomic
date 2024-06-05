package es.ucm.luisegui.dunktomic.infrastructure.database.adapters;

import es.ucm.luisegui.dunktomic.infrastructure.database.models.ImageEntity;
import es.ucm.luisegui.dunktomic.infrastructure.database.models.PlayerEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Component;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.HashSet;
import java.util.stream.Collectors;
import es.ucm.luisegui.dunktomic.domain.valueobjects.CourtPositions;

@Data
@Component
@AllArgsConstructor
public class PlayerDbAdapter
{
    private static final Logger log = LoggerFactory.getLogger(PlayerDbAdapter.class);
    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final JdbcClient jdbcClient;

    private static Set<String> castPositions(String positions) {
        if (positions == null)
            return new HashSet<>();
        return new HashSet<>(List.of(positions.split(",")));
    }

    private static PlayerEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new PlayerEntity(
            UUID.fromString(rs.getString(PlayerEntity.Field.ID)),
            rs.getString(PlayerEntity.Field.EMAIL),
            rs.getString(PlayerEntity.Field.NAME),
            rs.getString(PlayerEntity.Field.ROLE),
            new ImageEntity(UUID.fromString(rs.getString("user_image_id")), rs.getString(ImageEntity.Field.PATH), rs.getString(ImageEntity.Field.MIME_TYPE)),
            castPositions(rs.getString(PlayerEntity.Field.COURT_POSITIONS)),
            rs.getFloat(PlayerEntity.Field.LEVEL)
        );
    }

    public Optional<PlayerEntity> find(UUID id) {
        return jdbcClient
            .sql("select " +
                "u.id, u.email, u.name, u.role, p.positions_on_court, p.level, i.id as user_image_id, i.path, i.mime_type " +
                "from User u " +
                "left join Player p on " +
                "u.email = p.email " +
                "left join Image i on " +
                "u.user_image = i.id " +
                "where u.id = ?")
            .param(id.toString())
            .query(PlayerDbAdapter::mapRow)
            .optional();
    }

    public UUID save(PlayerEntity player) {
        if (find(player.getId()).isEmpty()) {
            StringBuilder sql = new StringBuilder("insert into User (id, email, name) values " +
                "(:id, :email, :name)");
            MapSqlParameterSource params = new MapSqlParameterSource();
            params.addValue("id", player.getId().toString());
            params.addValue("email", player.getEmail());
            params.addValue("name", player.getName());
            jdbcTemplate.update(sql.toString(), params);
            sql = new StringBuilder("insert into Player (email, positions_on_court) values " +
                "(:email, :positions_on_court)");
            params = new MapSqlParameterSource();
            params.addValue("email", player.getEmail());
            String positions = player.getCourtPositions().stream().map(CourtPositions::fromValueAsString).collect(Collectors.joining(","));
            params.addValue("positions_on_court", positions);
            jdbcTemplate.update(sql.toString(), params);
        } else {
            StringBuilder sql = new StringBuilder("update User set " +
                "email = :email, name = :name where id = :id");
            MapSqlParameterSource params = new MapSqlParameterSource();
            params.addValue("id", player.getId().toString());
            params.addValue("email", player.getEmail());
            params.addValue("name", player.getName());
            jdbcTemplate.update(sql.toString(), params);
            sql = new StringBuilder("update Player set " +
                "email = :email, positions_on_court = :positions_on_court where email = :email");
            params = new MapSqlParameterSource();
            params.addValue("email", player.getEmail());
            String positions = player.getCourtPositions().stream().map(CourtPositions::fromValueAsString).collect(Collectors.joining(","));
            params.addValue("positions_on_court", positions);
            jdbcTemplate.update(sql.toString(), params);
        }
        return player.getId();
    }

}
