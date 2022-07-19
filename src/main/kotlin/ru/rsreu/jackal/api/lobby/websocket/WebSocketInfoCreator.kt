package ru.rsreu.jackal.api.lobby.websocket

import org.springframework.stereotype.Component
import ru.rsreu.jackal.configuration.WebSocketParametersConfiguration
import ru.rsreu.jackal.shared_models.WebSocketInfo
import java.util.*

@Component
class WebSocketInfoCreator(
    private val webSocketParametersConfiguration: WebSocketParametersConfiguration
) {
    fun of(lobbyId: UUID, userId: Long) = WebSocketInfo(
        webSocketParametersConfiguration.subscriptionLobbyPattern + lobbyId.toString(),
        webSocketParametersConfiguration.subscriptionUserPattern + userId.toString()
    )
}