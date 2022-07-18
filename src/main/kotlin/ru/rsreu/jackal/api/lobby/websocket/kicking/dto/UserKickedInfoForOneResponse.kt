package ru.rsreu.jackal.api.lobby.websocket.kicking.dto

import ru.rsreu.jackal.api.lobby.websocket.WebSocketResponseType

data class UserKickedInfoForOneResponse(
    val type: WebSocketResponseType = WebSocketResponseType.KICKED_INFO_FOR_ONE
)