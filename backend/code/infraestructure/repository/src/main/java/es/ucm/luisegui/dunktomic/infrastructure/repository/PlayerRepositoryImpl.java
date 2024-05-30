package es.ucm.luisegui.dunktomic.infrastructure.repository;

import es.ucm.luisegui.dunktomic.domain.entities.Player;
import es.ucm.luisegui.dunktomic.domain.repositories.PlayerRepository;
import es.ucm.luisegui.dunktomic.domain.valueobjects.EntityId;
import es.ucm.luisegui.dunktomic.infrastructure.database.adapters.PlayerDbAdapter;
import es.ucm.luisegui.dunktomic.infrastructure.database.models.PlayerEntity;
import es.ucm.luisegui.dunktomic.infrastructure.keycloakclient.api.UsersApi;
import es.ucm.luisegui.dunktomic.infrastructure.repository.dto.PlayerAccount;
import es.ucm.luisegui.dunktomic.infrastructure.repository.mappers.PlayerMapper;
import es.ucm.luisegui.dunktomic.infrastructure.keycloakclient.model.UserRepresentation;
import org.springframework.stereotype.Repository;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Repository
public class PlayerRepositoryImpl implements PlayerRepository
{
    private final UsersApi usersApi;
    private final PlayerDbAdapter playerDbAdapter;
    private static final String REALM = "master";

    public PlayerRepositoryImpl(UsersApi usersApi, PlayerDbAdapter playerDbAdapter) {
        this.usersApi = usersApi;
        this.playerDbAdapter = playerDbAdapter;
    }

    @Override
    public Optional<Player> find(EntityId id) {
        if (id == null)
            return Optional.empty();

        PlayerMapper playerMapper = new PlayerMapper();
        Optional<PlayerEntity> playerEntity = playerDbAdapter.find(id.getId());
        return playerEntity.map(playerMapper::toEntity);
    }

    @Override
    public EntityId save(Player player, String password) {
        if (player.getId() == null) {
            UserRepresentation userRepresentation = PlayerAccount.toUserRepresentation(player, password);
            // TODO: Check if user already exists, then throw exception
            usersApi.createUser(REALM, userRepresentation).block();
            String userId = Objects.requireNonNull(usersApi.getUsers(REALM, null, player.getEmail(), false, true,
                null, 0, null, null, null, null, null, null, null
                , null).blockFirst()).getId();
            assert userId != null;
            player.setId(new EntityId(UUID.fromString(userId)));
            PlayerMapper playerMapper = new PlayerMapper();
            playerDbAdapter.save(playerMapper.fromEntity(player));
            return player.getId();
        } else {
            // TODO: Update player
            return null;
        }
    }
}
