package ru.rsreu.jackal.api.lobby.websocket.leaving.dto

import ru.rsreu.jackal.api.lobby.websocket.WebSocketMessage
import ru.rsreu.jackal.api.lobby.websocket.WebSocketMessageType

data class UserLeavingErrorResponse(
    val userLeavingErrorType: UserLeavingErrorType
) : WebSocketMessage(WebSocketMessageType.LEAVING_ERROR)

enum class UserLeavingErrorType {
    ALREADY_NOT_IN_LOBBY, USER_IN_GAME, INVALID_TOKEN, LOBBY_NOT_EXISTS
}
