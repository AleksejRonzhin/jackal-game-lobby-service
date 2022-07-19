package ru.rsreu.jackal.api.lobby.websocket.kicking.dto

import ru.rsreu.jackal.api.lobby.websocket.WebSocketMessage
import ru.rsreu.jackal.api.lobby.websocket.WebSocketMessageType

data class UserKickedInfoForAllResponse(
    val kickedUserId: Long
) : WebSocketMessage(WebSocketMessageType.KICKED_INFO_FOR_ALL)