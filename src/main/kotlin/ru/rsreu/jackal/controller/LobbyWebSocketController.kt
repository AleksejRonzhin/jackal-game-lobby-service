package ru.rsreu.jackal.controller

import org.springframework.messaging.handler.annotation.DestinationVariable
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.messaging.handler.annotation.SendTo
import org.springframework.messaging.simp.SimpMessagingTemplate
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Controller

@Controller
class LobbyWebSocketController(val template: SimpMessagingTemplate) {
    @MessageMapping("/connect/{lobbyId}")
    @SendTo("/topic/lobby/{lobbyId}")
    fun connectToLobby(@DestinationVariable lobbyId: String, authentication: Authentication): Authentication {
        return authentication
    }
}