package ru.rsreu.jackal.controller

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import ru.rsreu.jackal.shared_models.WebSocketInfo

@Component
class WebSocketInfoCreator(
    @Value("\${web_sockets.lobby_pattern}")
    private var subscriptionLobbyPattern: String,
    @Value("\${web_sockets.user_pattern}")
    private val subscriptionUserPattern: String
) {
    fun of(lobbyId: Long, userId: Long) = WebSocketInfo(
        subscriptionLobbyPattern + lobbyId.toString(),
        subscriptionUserPattern + userId.toString()
    )
}