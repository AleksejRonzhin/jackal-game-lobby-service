package ru.rsreu.jackal.websocket.kicking.dto

import ru.rsreu.jackal.websocket.WebSocketResponseType

data class UserKickingErrorResponse(
    val type: WebSocketResponseType = WebSocketResponseType.KICKING_ERROR,
    val userKickingErrorType: UserKickingErrorType
)

enum class UserKickingErrorType {
    INVALID_TOKEN, LOBBY_NOT_FOUND, NOT_HOST, HOST_SELF_KICKING, KICKABLE_NOT_IN_LOBBY, KICKABLE_IN_GAME
}