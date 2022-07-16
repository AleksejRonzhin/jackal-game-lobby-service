package ru.rsreu.jackal.websocket.status_changing

import org.springframework.messaging.handler.annotation.DestinationVariable
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.messaging.handler.annotation.SendTo
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Controller
import ru.rsreu.jackal.service.LobbyService
import ru.rsreu.jackal.websocket.WebSocketUtil
import ru.rsreu.jackal.websocket.status_changing.dto.UserChangedStatusInfoForAllResponse

@Controller
class UserChangingStatusChangingController(
    private val wsUtil: WebSocketUtil,
    private val lobbyService: LobbyService
) {
    @MessageMapping("/change-state/{lobbyId}")
    @SendTo("/topic/lobby/{lobbyId}")
    fun changeLobbyMemberStatus(
        @DestinationVariable lobbyId: Long,
        authentication: Authentication
    ): UserChangedStatusInfoForAllResponse {
        val userId = authentication.principal.toString().toLong()
        wsUtil.validateTokenForLobbyId(lobbyId, authentication.credentials.toString().toLong(), userId)
        val member = lobbyService.changeUserStateAndGetInfo(userId, lobbyId)
        return UserChangedStatusInfoForAllResponse(lobbyMemberInfo = member)
    }
}