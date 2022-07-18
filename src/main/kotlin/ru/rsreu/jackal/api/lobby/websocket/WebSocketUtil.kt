package ru.rsreu.jackal.api.lobby.websocket

import org.springframework.messaging.simp.SimpMessagingTemplate
import org.springframework.stereotype.Component
import ru.rsreu.jackal.configuration.WebSocketParametersConfiguration
import ru.rsreu.jackal.exception.InvalidForLobbyTokenException

@Component
class WebSocketUtil(
    private val template: SimpMessagingTemplate,
    private val webSocketParametersConfiguration: WebSocketParametersConfiguration
) {
    fun sendInfoForOne(userId: Long, body: Any) {
        template.convertAndSend(webSocketParametersConfiguration.subscriptionUserPattern + userId, body)
    }

    fun validateTokenForLobbyId(lobbyId: Long, lobbyIdFromAuth: Long, userId: Long) {
        if (lobbyId != lobbyIdFromAuth) {
            throw InvalidForLobbyTokenException(userId)
        }
    }
}