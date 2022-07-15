package ru.rsreu.jackal.websocket.leaving.dto

import ru.rsreu.jackal.websocket.WebSocketResponseType

data class LeavingErrorResponse(
    val type: WebSocketResponseType = WebSocketResponseType.LEAVING_ERROR,
    val leavingErrorType: LeavingErrorType
)

enum class LeavingErrorType {
    ALREADY_NOT_IN_LOBBY, USER_IN_GAME, INVALID_TOKEN, LOBBY_NOT_EXISTS
}
