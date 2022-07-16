package ru.rsreu.jackal.websocket.kicking

import org.springframework.messaging.handler.annotation.DestinationVariable
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.messaging.handler.annotation.Payload
import org.springframework.messaging.handler.annotation.SendTo
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Controller
import ru.rsreu.jackal.service.LobbyService
import ru.rsreu.jackal.websocket.WebSocketUtil
import ru.rsreu.jackal.websocket.kicking.dto.UserKickedInfoForAllResponse
import ru.rsreu.jackal.websocket.kicking.dto.UserKickedInfoForOneResponse
import ru.rsreu.jackal.websocket.kicking.dto.UserKickingBody

@Controller
class UserKickingController(
    private val wsUtil: WebSocketUtil,
    private val lobbyService: LobbyService
) {
    @MessageMapping("/kick/{lobbyId}")
    @SendTo("/topic/lobby/{lobbyId}")
    fun kickLobbyMember(
        @DestinationVariable lobbyId: Long,
        @Payload kickingBody: UserKickingBody,
        authentication: Authentication
    ): UserKickedInfoForAllResponse {
        val hostId = authentication.principal.toString().toLong()
        wsUtil.validateTokenForLobbyId(lobbyId, authentication.credentials.toString().toLong(), hostId)
        val kickableUserId = kickingBody.kickableUserId
        lobbyService.kickUserFromLobby(
            hostId = hostId,
            lobbyId = lobbyId,
            kickableUserId = kickableUserId
        )
        wsUtil.sendInfoForOne(kickableUserId, UserKickedInfoForOneResponse())
        return UserKickedInfoForAllResponse(kickedUserId = kickableUserId)
    }
}