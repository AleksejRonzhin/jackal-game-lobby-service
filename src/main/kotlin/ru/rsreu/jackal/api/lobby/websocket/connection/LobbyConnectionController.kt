package ru.rsreu.jackal.api.lobby.websocket.connection

import org.springframework.messaging.handler.annotation.DestinationVariable
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.messaging.handler.annotation.SendTo
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Controller
import ru.rsreu.jackal.api.lobby.service.LobbyService
import ru.rsreu.jackal.api.lobby.websocket.connection.dto.UserConnectedInfoForAllResponse
import ru.rsreu.jackal.api.lobby.websocket.connection.dto.UserConnectedInfoForOneResponse
import java.util.*

@Controller
class LobbyConnectionController(
    private val wsUtil: ru.rsreu.jackal.api.lobby.websocket.WebSocketUtil,
    private val lobbyService: LobbyService,
) {
    @MessageMapping("/connect/{lobbyId}")
    @SendTo("/topic/lobby/{lobbyId}")
    fun connectToLobby(
        @DestinationVariable lobbyId: UUID,
        authentication: Authentication
    ): UserConnectedInfoForAllResponse {
        val userId = authentication.principal.toString().toLong()
        wsUtil.validateTokenForLobbyId(lobbyId, UUID.fromString(authentication.credentials.toString()), userId)
        val (hostId, members) = lobbyService.connectUserAndGetHostIdAndAllMembers(userId, lobbyId)
        wsUtil.sendInfoForOne(
            userId, UserConnectedInfoForOneResponse(
                usersInLobby = members,
                hostId = hostId
            )
        )
        return UserConnectedInfoForAllResponse(
            connectedUserId = authentication.principal.toString().toLong()
        )
    }
}