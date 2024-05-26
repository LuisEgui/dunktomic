package es.ucm.luisegui.dunktomic.controllers;

import es.ucm.luisegui.dunktomic.application.dtos.FindClubCourtsDto;
import es.ucm.luisegui.dunktomic.application.dtos.FindClubsFilters;
import es.ucm.luisegui.dunktomic.application.usecases.FindClubCourtsUseCase;
import es.ucm.luisegui.dunktomic.application.usecases.FindClubUseCase;
import es.ucm.luisegui.dunktomic.application.usecases.FindClubsUseCase;
import es.ucm.luisegui.dunktomic.dtos.ClubDto;
import es.ucm.luisegui.dunktomic.dtos.ClubsPageDto;
import es.ucm.luisegui.dunktomic.dtos.CourtsPageDto;
import es.ucm.luisegui.dunktomic.rest.ClubsApi;
import es.ucm.luisegui.dunktomic.rest.dtos.Club;
import es.ucm.luisegui.dunktomic.rest.dtos.ClubsPage;
import es.ucm.luisegui.dunktomic.rest.dtos.CourtsPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

@RestController
public class ClubsController implements ClubsApi
{
    private static final int DEFAULT_PAGE = 0;
    private static final int DEFAULT_SIZE = 10;

    private final FindClubUseCase findClubUseCase;
    private final FindClubsUseCase findClubsUseCase;
    private final FindClubCourtsUseCase findClubCourtsUseCase;

    @Autowired
    public ClubsController(FindClubUseCase findClubUseCase, FindClubsUseCase findClubsUseCase, FindClubCourtsUseCase findClubCourtsUseCase) {
        this.findClubUseCase = findClubUseCase;
        this.findClubsUseCase = findClubsUseCase;
        this.findClubCourtsUseCase = findClubCourtsUseCase;
    }

    @Override
    public ResponseEntity<Club> getClub(String id, String acceptLanguage) {
        es.ucm.luisegui.dunktomic.domain.entities.Club club = findClubUseCase.execute(id);
        return ResponseEntity.ok().body(ClubDto.toModel(club));
    }

    @Override
    public ResponseEntity<ClubsPage> getClubs(String acceptLanguage, String name, String district, String postalCode, String streetAddress, Integer page, Integer size) {
        FindClubsFilters filters = new FindClubsFilters(decodeUrl(name), district, postalCode, streetAddress, pageRequestOf(page, size));
        Page<es.ucm.luisegui.dunktomic.domain.entities.Club> clubs = findClubsUseCase.execute(filters);
        return ResponseEntity.ok().body(ClubsPageDto.toModel(clubs));
    }

    @Override
    public ResponseEntity<CourtsPage> getClubCourts(String id, String acceptLanguage, Integer page) {
        FindClubCourtsDto input = new FindClubCourtsDto(id, pageRequestOf(page, null));
        Page<es.ucm.luisegui.dunktomic.domain.entities.Court> courts = findClubCourtsUseCase.execute(input);
        return ResponseEntity.ok().body(CourtsPageDto.toModel(courts));
    }

    private static Pageable pageRequestOf(Integer page, Integer size) {
        if (page != null || size != null) {
            int limitedSize = (size == null || size > DEFAULT_SIZE) ? DEFAULT_SIZE : size;
            return PageRequest.of((page == null ? DEFAULT_PAGE : page), limitedSize);
        } else {
            return Pageable.unpaged();
        }
    }

    private String decodeUrl(String value) {
        if (value == null)
            return null;
        try {
            return URLDecoder.decode(value, StandardCharsets.UTF_8.toString());
        } catch (UnsupportedEncodingException e) {
            throw new IllegalArgumentException("Invalid encoding for parameter: " + value, e);
        }
    }

}
