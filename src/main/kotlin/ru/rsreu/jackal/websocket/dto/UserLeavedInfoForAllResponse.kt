package ru.rsreu.jackal.websocket.dto

data class UserLeavedInfoForAllResponse(
    val type: WebSocketResponseType = WebSocketResponseType.LEAVED_INFO_FOR_ALL,
    val leavedUserId: Long,
    val hostId: Long?
)