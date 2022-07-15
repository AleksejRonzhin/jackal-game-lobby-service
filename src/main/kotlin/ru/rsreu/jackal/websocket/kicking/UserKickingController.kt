package ru.rsreu.jackal.websocket.kicking

import org.springframework.messaging.handler.annotation.DestinationVariable
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.messaging.handler.annotation.Payload
import org.springframework.messaging.handler.annotation.SendTo
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Controller
import ru.rsreu.jackal.service.LobbyService
import ru.rsreu.jackal.websocket.WebSocketUtil
import ru.rsreu.jackal.websocket.kicking.dto.LobbyMemberKickingBody

@Controller
class LobbyMemberKickingController(
    private val wsUtil: WebSocketUtil,
    private val lobbyService: LobbyService
) {
    @MessageMapping("/kick/{lobbyId}")
    @SendTo("/topic/lobby/{lobbyId}")
    fun kickLobbyMember(
        @DestinationVariable lobbyId: Long,
        @Payload kickingBody: LobbyMemberKickingBody,
        authentication: Authentication
    ) {
        val hostId = authentication.principal.toString().toLong()
        wsUtil.validateTokenForLobbyId(lobbyId, authentication.credentials.toString().toLong(), hostId)

    }
}