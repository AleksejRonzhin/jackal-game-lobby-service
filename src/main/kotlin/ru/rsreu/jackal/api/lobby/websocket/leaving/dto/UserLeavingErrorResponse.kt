package ru.rsreu.jackal.api.lobby.websocket.leaving.dto

import ru.rsreu.jackal.api.lobby.websocket.WebSocketResponseType

data class UserLeavingErrorResponse(
    val type: WebSocketResponseType = WebSocketResponseType.LEAVING_ERROR,
    val userLeavingErrorType: UserLeavingErrorType
)

enum class UserLeavingErrorType {
    ALREADY_NOT_IN_LOBBY, USER_IN_GAME, INVALID_TOKEN, LOBBY_NOT_EXISTS
}
