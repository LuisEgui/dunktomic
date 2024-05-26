package es.ucm.luisegui.dunktomic.infrastructure.database.adapters;

import es.ucm.luisegui.dunktomic.infrastructure.database.models.ClubEntity;
import es.ucm.luisegui.dunktomic.infrastructure.database.models.CourtEntity;
import es.ucm.luisegui.dunktomic.infrastructure.database.models.ImageEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Component;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Data
@Component
@AllArgsConstructor
public class ClubDbAdapter
{
    private static final Logger log = LoggerFactory.getLogger(ClubDbAdapter.class);
    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final JdbcClient jdbcClient;

    private static final int DEFAULT_PAGE_SIZE = 100;
    private static final int DEFAULT_PAGE_NUMBER = 0;

    private static ClubEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new ClubEntity(
            UUID.fromString(rs.getString(ClubEntity.Field.ID)),
            rs.getString(ClubEntity.Field.NAME),
            rs.getString(ClubEntity.Field.DISTRICT),
            rs.getString(ClubEntity.Field.STREET_ADDRESS),
            rs.getString(ClubEntity.Field.POSTAL_CODE),
            new ImageEntity(UUID.fromString(rs.getString(ImageEntity.Field.ID)), rs.getString(ImageEntity.Field.PATH), rs.getString(ImageEntity.Field.MIME_TYPE)),
            rs.getFloat(ClubEntity.Field.LATITUDE),
            rs.getFloat(ClubEntity.Field.LONGITUDE)
        );
    }

    private static CourtEntity mapRowCourt(ResultSet rs, int rowNum) throws SQLException {
        return new CourtEntity(
            UUID.fromString(rs.getString(CourtEntity.Field.CLUB_ID)),
            rs.getString(CourtEntity.Field.NAME),
            rs.getString(CourtEntity.Field.TYPE)
        );
    }

    public Optional<ClubEntity> find(UUID id) {
        return jdbcClient
            .sql("select " +
                "c.id, c.name, c.district, c.street_address, c.postal_code, i.path, i.mime_type, c.latitude, c.longitude " +
                "from Club c " +
                "left join Image i on c.club_image = i.id " +
                "where c.id = ?")
            .param(id.toString())
            .query(ClubDbAdapter::mapRow)
            .optional();
    }

    public Page<ClubEntity> find(String name, String district, String postalCode, String streetAddress, Pageable pageable) {
        if (pageable == null)
            pageable = PageRequest.of(0, DEFAULT_PAGE_SIZE);

        int pageSize = pageable.isPaged() ? pageable.getPageSize() : DEFAULT_PAGE_SIZE;
        int pageNumber = pageable.isPaged() ? pageable.getPageNumber() : DEFAULT_PAGE_NUMBER;

        StringBuilder sql = new StringBuilder(("select " +
            "c.id, c.name, c.district, c.street_address, c.postal_code, i.path, i.mime_type, c.latitude, c.longitude " +
            "from Club c " +
            "left join Image i on c.club_image = i.id " +
            "where 1=1 "));

        MapSqlParameterSource params = new MapSqlParameterSource();

        if (name != null) {
            sql.append("and (match(c.name) against(:name in natural language mode) or c.name is null) ");
            params.addValue("name", name);
        }

        if (district != null) {
            sql.append("and (c.district = :district or c.district is null) ");
            params.addValue("district", district);
        }

        if (postalCode != null) {
            sql.append("and (c.postal_code = :postal_code or c.postal_code is null) ");
            params.addValue("postal_code", postalCode);
        }

        if (streetAddress != null) {
            sql.append("and (c.street_address = :street_address or c.street_address is null) ");
            params.addValue("street_address", streetAddress);
        }

        sql.append((" limit :limit "));
        params.addValue("limit", pageSize);
        sql.append((" offset :offset "));
        params.addValue("offset", pageNumber * pageSize);

        List<ClubEntity> clubs = jdbcTemplate.query(sql.toString(),
            params,
            ClubDbAdapter::mapRow);

        if (clubs.isEmpty())
            return Page.empty();

        return new PageImpl<>(clubs, pageable, clubs.size());
    }

    public Page<CourtEntity> findClubCourts(UUID id, Pageable pageable) {
        if (pageable == null)
            pageable = PageRequest.of(0, DEFAULT_PAGE_SIZE);

        int pageSize = pageable.isPaged() ? pageable.getPageSize() : DEFAULT_PAGE_SIZE;
        int pageNumber = pageable.isPaged() ? pageable.getPageNumber() : DEFAULT_PAGE_NUMBER;

        StringBuilder sql = new StringBuilder("select " +
            "cc.club_id, cc.name, cc.type " +
            "from Court cc " +
            "where cc.club_id = :id " +
            "limit :limit offset :offset");

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", id.toString());
        params.addValue("limit", pageSize);
        params.addValue("offset", pageNumber * pageSize);

        List<CourtEntity> courts = jdbcTemplate.query(
            sql.toString(),
            params,
            ClubDbAdapter::mapRowCourt);

        if (courts.isEmpty())
            return Page.empty();

        return new PageImpl<>(courts, pageable, courts.size());
    }

}
