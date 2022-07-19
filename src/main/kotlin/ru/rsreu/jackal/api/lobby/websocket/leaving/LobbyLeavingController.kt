package ru.rsreu.jackal.api.lobby.websocket.leaving

import org.springframework.messaging.handler.annotation.DestinationVariable
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.messaging.handler.annotation.SendTo
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Controller
import ru.rsreu.jackal.api.lobby.service.LobbyService
import ru.rsreu.jackal.api.lobby.websocket.WebSocketUtil
import ru.rsreu.jackal.api.lobby.websocket.leaving.dto.UserLeavedInfoForAllResponse
import ru.rsreu.jackal.api.lobby.websocket.leaving.dto.UserLeavedInfoForOneResponse
import java.util.*

@Controller
class LobbyLeavingController(
    private val wsUtil: WebSocketUtil,
    private val lobbyService: LobbyService
) {
    @MessageMapping("/leave/{lobbyId}")
    @SendTo("/topic/lobby/{lobbyId}")
    fun leaveFromLobby(
        @DestinationVariable lobbyId: UUID,
        authentication: Authentication
    ): UserLeavedInfoForAllResponse {
        val userId = authentication.principal.toString().toLong()
        wsUtil.validateTokenForLobbyId(lobbyId, UUID.fromString(authentication.credentials.toString()), userId)
        val newHostId = lobbyService.disconnectUserAndGetHostId(userId, lobbyId)
        wsUtil.sendInfoForOne(userId, UserLeavedInfoForOneResponse())
        return UserLeavedInfoForAllResponse(
            userId,
            newHostId
        )
    }
}