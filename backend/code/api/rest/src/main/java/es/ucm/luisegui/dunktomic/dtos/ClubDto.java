package es.ucm.luisegui.dunktomic.dtos;

import es.ucm.luisegui.dunktomic.rest.dtos.Club;

public class ClubDto
{
    public static Club toModel(es.ucm.luisegui.dunktomic.domain.entities.Club club) {
        return club != null ?
            new Club()
                .id(club.getId().getId())
                .name(club.getName())
                .district(club.getDistrict())
                .postalCode(club.getPostalCode())
                .streetAddress(club.getStreetAddress())
                .latitude(club.getLatitude())
                .longitude(club.getLongitude())
                .image(new ImageDto().toModel(club.getImage())) :
            null;
    }
}
