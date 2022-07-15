package ru.rsreu.jackal.configuration

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration

@Configuration
class WebSocketParametersConfiguration(
    @Value("\${web_sockets.lobby_pattern}")
    val subscriptionLobbyPattern: String,
    @Value("\${web_sockets.user_pattern}")
    val subscriptionUserPattern: String
)