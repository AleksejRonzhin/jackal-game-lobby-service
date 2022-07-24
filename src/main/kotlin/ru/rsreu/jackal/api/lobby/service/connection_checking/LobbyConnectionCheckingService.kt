package ru.rsreu.jackal.api.lobby.service.connection_checking

import org.springframework.stereotype.Service
import ru.rsreu.jackal.api.lobby.Lobby
import ru.rsreu.jackal.api.lobby.LobbyConnectionCheckingEntity
import ru.rsreu.jackal.api.lobby.repository.LobbyConnectionCheckingEntityRepository
import ru.rsreu.jackal.api.lobby.service.connection_checking.dto.CheckConnectionWebSocketRequest
import ru.rsreu.jackal.api.lobby.websocket.WebSocketUtil
import ru.rsreu.jackal.exception.UsersNotHaveActiveConnectionException
import java.util.*

@Service
class LobbyConnectionCheckingService(
    private val repository: LobbyConnectionCheckingEntityRepository,
    private val connectionChecker: UserConnectionChecker,
    private val wsUtil: WebSocketUtil
) {
    fun checkLobbyMembersConnectionOrThrow(lobby: Lobby) {
        val checkingEntity = LobbyConnectionCheckingEntity(lobby)
        repository.addCheck(checkingEntity)
        wsUtil.sendInfoForLobby(lobby.id, CheckConnectionWebSocketRequest())
        val task = UserConnectionCheckTask(checkingEntity)
        connectionChecker.addCheckConnectionTask(task).get()
        val notConnectedUsersIds = task.notConnectedUsersIds ?: emptyList()
        repository.removeByLobbyId(lobby.id)
        if (notConnectedUsersIds.isNotEmpty()) {
            throw UsersNotHaveActiveConnectionException(lobby, notConnectedUsersIds.toList())
        }
    }

    fun markUserAsConnect(lobbyId: UUID, userId: Long) {
        val connectionCheckingEntity = repository.getByLobbyId(lobbyId) ?: return
        connectionCheckingEntity.membersStatuses[userId] = true
    }
}