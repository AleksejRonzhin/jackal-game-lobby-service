package ru.rsreu.jackal.websocket.kicking.dto

import ru.rsreu.jackal.websocket.WebSocketResponseType

data class UserKickedInfoForOneResponse(
    val type: WebSocketResponseType = WebSocketResponseType.KICKED_INFO_FOR_ONE
)