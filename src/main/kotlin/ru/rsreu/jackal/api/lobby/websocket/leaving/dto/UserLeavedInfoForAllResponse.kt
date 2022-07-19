package ru.rsreu.jackal.api.lobby.websocket.leaving.dto

import ru.rsreu.jackal.api.lobby.websocket.WebSocketMessage
import ru.rsreu.jackal.api.lobby.websocket.WebSocketMessageType

data class UserLeavedInfoForAllResponse(
    val leavedUserId: Long, val hostId: Long?
) : WebSocketMessage(WebSocketMessageType.LEAVED_INFO_FOR_ALL)