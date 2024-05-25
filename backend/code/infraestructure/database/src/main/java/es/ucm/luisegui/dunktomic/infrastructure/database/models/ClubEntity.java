package es.ucm.luisegui.dunktomic.infrastructure.database.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClubEntity
{
    @Id
    private UUID id;
    private String name;
    private String district;
    private String streetAddress;
    private String postalCode;
    private ImageEntity image;
    private Float latitude;
    private Float longitude;

    public static class Field
    {
        public static final String TABLE = "Club";
        public static final String ID = "id";
        public static final String NAME = "name";
        public static final String DISTRICT = "district";
        public static final String STREET_ADDRESS = "street_address";
        public static final String POSTAL_CODE = "postal_code";
        public static final String IMAGE = "club_image";
        public static final String LATITUDE = "latitude";
        public static final String LONGITUDE = "longitude";
    }
}
