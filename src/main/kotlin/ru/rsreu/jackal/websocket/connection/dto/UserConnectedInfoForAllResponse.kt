package ru.rsreu.jackal.websocket.connection.dto

import ru.rsreu.jackal.websocket.WebSocketResponseType

data class UserConnectedInfoForAllResponse(
    val type: WebSocketResponseType = WebSocketResponseType.CONNECTED_INFO_FOR_ALL,
    val connectedUserId: Long
)