package ru.rsreu.jackal.websocket.status_changing.dto

import ru.rsreu.jackal.websocket.WebSocketResponseType

data class UserChangingStatusErrorResponse(
    val type: WebSocketResponseType = WebSocketResponseType.CHANGING_STATE_ERROR,
    val userChangingStatusErrorType: UserChangingStatusErrorType
)

enum class UserChangingStatusErrorType {
    INVALID_TOKEN, LOBBY_NOT_EXISTS, USER_NOT_CONNECTED, USER_IN_GAME
}