package ru.rsreu.jackal.websocket.connection.dto

import ru.rsreu.jackal.websocket.WebSocketResponseType

data class UserConnectionErrorResponse(
    val type: WebSocketResponseType = WebSocketResponseType.CONNECTION_ERROR,
    val userConnectionErrorType: UserConnectionErrorType
)

enum class UserConnectionErrorType {
    INVALID_TOKEN, LOBBY_NOT_EXISTS, NOT_FOUND_USER_FOR_THIS_CONNECTION_INFO
}