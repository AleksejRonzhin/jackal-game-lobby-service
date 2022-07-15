package ru.rsreu.jackal.controller

import org.springframework.stereotype.Component
import ru.rsreu.jackal.configuration.WebSocketParametersConfiguration
import ru.rsreu.jackal.shared_models.WebSocketInfo

@Component
class WebSocketInfoCreator(
    private val webSocketParametersConfiguration: WebSocketParametersConfiguration
) {
    fun of(lobbyId: Long, userId: Long) = WebSocketInfo(
        webSocketParametersConfiguration.subscriptionLobbyPattern + lobbyId.toString(),
        webSocketParametersConfiguration.subscriptionUserPattern + userId.toString()
    )
}