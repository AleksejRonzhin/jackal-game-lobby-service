package ru.rsreu.jackal.api.lobby.service.connection_checking.dto

import ru.rsreu.jackal.api.lobby.websocket.WebSocketMessage
import ru.rsreu.jackal.api.lobby.websocket.WebSocketMessageType

class CheckConnectionWebSocketRequest : WebSocketMessage(WebSocketMessageType.CHECK_CONNECTION_REQUEST)