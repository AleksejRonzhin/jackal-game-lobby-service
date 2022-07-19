package ru.rsreu.jackal.api.lobby.websocket.connection.dto

import ru.rsreu.jackal.api.lobby.websocket.WebSocketMessage
import ru.rsreu.jackal.api.lobby.websocket.WebSocketMessageType

data class UserConnectedInfoForAllResponse(
    val connectedUserId: Long
) : WebSocketMessage(WebSocketMessageType.CONNECTED_INFO_FOR_ALL)
