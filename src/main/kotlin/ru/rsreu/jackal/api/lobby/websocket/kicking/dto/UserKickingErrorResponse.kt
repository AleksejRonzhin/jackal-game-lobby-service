package ru.rsreu.jackal.api.lobby.websocket.kicking.dto

import ru.rsreu.jackal.api.lobby.websocket.WebSocketMessage
import ru.rsreu.jackal.api.lobby.websocket.WebSocketMessageType


data class UserKickingErrorResponse(
    val userKickingErrorType: UserKickingErrorType
) : WebSocketMessage(WebSocketMessageType.KICKING_ERROR)

enum class UserKickingErrorType {
    INVALID_TOKEN, LOBBY_NOT_FOUND, NOT_HOST, HOST_SELF_KICKING, KICKABLE_NOT_IN_LOBBY, KICKABLE_IN_GAME
}