package ru.rsreu.jackal.api.lobby.websocket.kicking

import org.springframework.messaging.handler.annotation.DestinationVariable
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.messaging.handler.annotation.Payload
import org.springframework.messaging.handler.annotation.SendTo
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Controller
import ru.rsreu.jackal.api.lobby.service.LobbyService
import ru.rsreu.jackal.api.lobby.websocket.kicking.dto.UserKickedInfoForAllResponse
import ru.rsreu.jackal.api.lobby.websocket.kicking.dto.UserKickedInfoForOneResponse
import ru.rsreu.jackal.api.lobby.websocket.kicking.dto.UserKickingBody
import java.util.*

@Controller
class UserKickingController(
    private val wsUtil: ru.rsreu.jackal.api.lobby.websocket.WebSocketUtil, private val lobbyService: LobbyService
) {
    @MessageMapping("/kick/{lobbyId}")
    @SendTo("/topic/lobby/{lobbyId}")
    fun kickLobbyMember(
        @DestinationVariable lobbyId: UUID, @Payload kickingBody: UserKickingBody, authentication: Authentication
    ): UserKickedInfoForAllResponse {
        val hostId = authentication.principal.toString().toLong()
        wsUtil.validateTokenForLobbyId(lobbyId, UUID.fromString(authentication.credentials.toString()), hostId)
        val kickableUserId = kickingBody.kickableUserId
        lobbyService.kickUserFromLobby(
            hostId = hostId, lobbyId = lobbyId, kickableUserId = kickableUserId
        )
        wsUtil.sendInfoForOne(kickableUserId, UserKickedInfoForOneResponse())
        return UserKickedInfoForAllResponse(kickedUserId = kickableUserId)
    }
}