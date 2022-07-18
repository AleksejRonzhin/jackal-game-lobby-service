package ru.rsreu.jackal.api.lobby.websocket.connection.dto

import ru.rsreu.jackal.api.lobby.websocket.WebSocketResponseType

data class UserConnectedInfoForAllResponse(
    val type: WebSocketResponseType = WebSocketResponseType.CONNECTED_INFO_FOR_ALL,
    val connectedUserId: Long
)
