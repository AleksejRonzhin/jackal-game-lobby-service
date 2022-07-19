package ru.rsreu.jackal.api.lobby.websocket.check_connection

import org.springframework.messaging.handler.annotation.DestinationVariable
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Controller
import ru.rsreu.jackal.api.lobby.service.connection_checking.LobbyConnectionCheckingService
import java.util.*

@Controller
class UserCheckConnectionController(private val connectionCheckingService: LobbyConnectionCheckingService) {

    @MessageMapping("/check-connection/{lobbyId}")
    fun checkConnection(
        @DestinationVariable
        lobbyId: UUID,
        authentication: Authentication
    ) {
        connectionCheckingService.markUserAsConnect(lobbyId, authentication.principal.toString().toLong())
    }
}