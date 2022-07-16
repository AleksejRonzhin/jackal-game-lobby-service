package ru.rsreu.jackal.websocket.kicking.dto

import ru.rsreu.jackal.websocket.WebSocketResponseType

data class UserKickedInfoForAllResponse(
    val type: WebSocketResponseType = WebSocketResponseType.KICKED_INFO_FOR_ALL,
    val kickedUserId: Long
)