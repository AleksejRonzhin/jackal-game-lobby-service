package ru.rsreu.jackal.websocket.dto

data class ConnectionErrorResponse(
    val type: WebSocketResponseType = WebSocketResponseType.CONNECTION_ERROR,
    val connectedErrorType: ConnectedErrorType
)

enum class ConnectedErrorType {
    INVALID_TOKEN, LOBBY_NOT_EXISTS, NOT_FIND_USER_FOR_THIS_CONNECTION_INFO
}