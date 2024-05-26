package es.ucm.luisegui.dunktomic.controllers;

import es.ucm.luisegui.dunktomic.application.dtos.FindClubsFilters;
import es.ucm.luisegui.dunktomic.application.usecases.FindClubUseCase;
import es.ucm.luisegui.dunktomic.application.usecases.FindClubsUseCase;
import es.ucm.luisegui.dunktomic.dtos.ClubDto;
import es.ucm.luisegui.dunktomic.dtos.ClubsPageDto;
import es.ucm.luisegui.dunktomic.rest.ClubsApi;
import es.ucm.luisegui.dunktomic.rest.dtos.Club;
import es.ucm.luisegui.dunktomic.rest.dtos.ClubsPage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private static final int DEFAULT_SIZE = 5;
    private static final Logger log = LoggerFactory.getLogger(ClubsController.class);

    private final FindClubUseCase findClubUseCase;
    private final FindClubsUseCase findClubsUseCase;

    @Autowired
    public ClubsController(FindClubUseCase findClubUseCase, FindClubsUseCase findClubsUseCase) {
        this.findClubUseCase = findClubUseCase;
        this.findClubsUseCase = findClubsUseCase;
    }

    @Override
    public ResponseEntity<Club> getClub(String id, String acceptLanguage) {
        es.ucm.luisegui.dunktomic.domain.entities.Club club = findClubUseCase.execute(id);
        return ResponseEntity.ok().body(ClubDto.toModel(club));
    }

    @Override
    public ResponseEntity<ClubsPage> getClubs(String acceptLanguage, String name, String district, String postalCode, String streetAddress, Integer page, Integer size) {
        log.info("Searching clubs with filters: name={}, district={}, postalCode={}, streetAddress={}, page={}, size={}", name, district, postalCode, streetAddress, page, size);
        FindClubsFilters filters = new FindClubsFilters(decodeUrl(name), district, postalCode, streetAddress, pageRequestOf(page, size));
        Page<es.ucm.luisegui.dunktomic.domain.entities.Club> clubs = findClubsUseCase.execute(filters);
        return ResponseEntity.ok().body(ClubsPageDto.toModel(clubs));
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
