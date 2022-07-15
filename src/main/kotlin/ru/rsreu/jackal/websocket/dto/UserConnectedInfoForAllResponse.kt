package ru.rsreu.jackal.websocket.dto

data class UserConnectedInfoForAllResponse(
    val type: WebSocketResponseType = WebSocketResponseType.CONNECTED_INFO_FOR_ALL,
    val connectedUserId: Long
)
