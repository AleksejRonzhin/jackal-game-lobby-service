package ru.rsreu.jackal.api.lobby.websocket.leaving.dto

import ru.rsreu.jackal.api.lobby.websocket.WebSocketResponseType

data class UserLeavedInfoForAllResponse(
    val type: WebSocketResponseType = WebSocketResponseType.LEAVED_INFO_FOR_ALL, val leavedUserId: Long, val hostId: Long?
)