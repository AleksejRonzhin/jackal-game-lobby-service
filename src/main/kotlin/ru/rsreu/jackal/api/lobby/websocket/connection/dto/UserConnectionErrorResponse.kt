package ru.rsreu.jackal.api.lobby.websocket.connection.dto

import ru.rsreu.jackal.api.lobby.websocket.WebSocketMessage
import ru.rsreu.jackal.api.lobby.websocket.WebSocketMessageType

data class UserConnectionErrorResponse(
    val userConnectionErrorType: UserConnectionErrorType
) : WebSocketMessage(WebSocketMessageType.CONNECTION_ERROR)

enum class UserConnectionErrorType {
    INVALID_TOKEN, LOBBY_NOT_EXISTS, NOT_FOUND_USER_FOR_THIS_CONNECTION_INFO
}