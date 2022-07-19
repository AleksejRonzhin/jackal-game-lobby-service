package ru.rsreu.jackal.api.lobby.websocket.status_changing.dto

import ru.rsreu.jackal.api.lobby.websocket.WebSocketMessage
import ru.rsreu.jackal.api.lobby.websocket.WebSocketMessageType

data class UserChangingStatusErrorResponse(
    val userChangingStatusErrorType: UserChangingStatusErrorType
) : WebSocketMessage(WebSocketMessageType.CHANGING_STATE_ERROR)

enum class UserChangingStatusErrorType {
    INVALID_TOKEN, LOBBY_NOT_EXISTS, USER_NOT_CONNECTED, USER_IN_GAME
}