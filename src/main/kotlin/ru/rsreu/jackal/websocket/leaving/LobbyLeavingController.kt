package ru.rsreu.jackal.websocket.leaving

import org.springframework.messaging.handler.annotation.DestinationVariable
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.messaging.handler.annotation.SendTo
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Controller
import ru.rsreu.jackal.service.LobbyService
import ru.rsreu.jackal.websocket.WebSocketResponseType
import ru.rsreu.jackal.websocket.WebSocketUtil
import ru.rsreu.jackal.websocket.leaving.dto.UserLeavedInfoForAllResponse
import ru.rsreu.jackal.websocket.leaving.dto.UserLeavedInfoForOneResponse

@Controller
class LobbyLeavingController(
    private val wsUtil: WebSocketUtil,
    private val lobbyService: LobbyService
) {
    @MessageMapping("/leave/{lobbyId}")
    @SendTo("/topic/lobby/{lobbyId}")
    fun leaveFromLobby(
        @DestinationVariable lobbyId: Long,
        authentication: Authentication
    ): UserLeavedInfoForAllResponse {
        val userId = authentication.principal.toString().toLong()
        wsUtil.validateTokenForLobbyId(lobbyId, authentication.credentials.toString().toLong(), userId)
        val newHostId = lobbyService.disconnectUserAndGetHostId(userId, lobbyId)
        wsUtil.sendInfoForOne(userId, UserLeavedInfoForOneResponse())
        return UserLeavedInfoForAllResponse(
            WebSocketResponseType.LEAVED_INFO_FOR_ALL,
            userId,
            newHostId
        )
    }
}