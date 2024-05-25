package es.ucm.luisegui.dunktomic.infrastructure.database.adapters;

import es.ucm.luisegui.dunktomic.infrastructure.database.models.ClubEntity;
import es.ucm.luisegui.dunktomic.infrastructure.database.models.ImageEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Component;
import java.util.Optional;
import java.util.UUID;

@Data
@Component
@AllArgsConstructor
public class ClubDbAdapter
{
    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final JdbcClient jdbcClient;

    public Optional<ClubEntity> findClubById(UUID id) {
        return jdbcClient
            .sql("select " +
                "c.id, c.name, c.district, c.street_address, c.postal_code, i.path, i.mime_type, c.latitude, c.longitude " +
                "from Club c " +
                "left join Image i on c.club_image = i.id " +
                "where c.id = ?")
            .param(id.toString())
            .query((rs, rowNum) -> new ClubEntity(
                UUID.fromString(rs.getString("id")),
                rs.getString(ClubEntity.Field.NAME),
                rs.getString(ClubEntity.Field.DISTRICT),
                rs.getString(ClubEntity.Field.STREET_ADDRESS),
                rs.getString(ClubEntity.Field.POSTAL_CODE),
                new ImageEntity(UUID.fromString(rs.getString(ImageEntity.Field.ID)), rs.getString(ImageEntity.Field.PATH), rs.getString(ImageEntity.Field.MIME_TYPE)),
                rs.getFloat(ClubEntity.Field.LATITUDE),
                rs.getFloat(ClubEntity.Field.LONGITUDE)
            ))
            .optional();
    }

}
