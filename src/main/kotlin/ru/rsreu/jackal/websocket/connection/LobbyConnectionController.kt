package ru.rsreu.jackal.websocket.connection

import org.springframework.messaging.handler.annotation.DestinationVariable
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.messaging.handler.annotation.SendTo
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Controller
import ru.rsreu.jackal.service.LobbyService
import ru.rsreu.jackal.websocket.WebSocketUtil
import ru.rsreu.jackal.websocket.connection.dto.UserConnectedInfoForAllResponse
import ru.rsreu.jackal.websocket.connection.dto.UserConnectedInfoForOneResponse

@Controller
class LobbyConnectionController(
    private val wsUtil: WebSocketUtil,
    private val lobbyService: LobbyService,
) {
    @MessageMapping("/connect/{lobbyId}")
    @SendTo("/topic/lobby/{lobbyId}")
    fun connectToLobby(
        @DestinationVariable lobbyId: Long,
        authentication: Authentication
    ): UserConnectedInfoForAllResponse {
        val userId = authentication.principal.toString().toLong()
        wsUtil.validateTokenForLobbyId(lobbyId, authentication.credentials.toString().toLong(), userId)
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