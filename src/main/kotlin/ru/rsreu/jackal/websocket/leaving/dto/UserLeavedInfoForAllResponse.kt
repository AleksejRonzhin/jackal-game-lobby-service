package ru.rsreu.jackal.websocket.leaving.dto

import ru.rsreu.jackal.websocket.WebSocketResponseType

data class UserLeavedInfoForAllResponse(
    val type: WebSocketResponseType = WebSocketResponseType.LEAVED_INFO_FOR_ALL,
    val leavedUserId: Long,
    val hostId: Long?
)