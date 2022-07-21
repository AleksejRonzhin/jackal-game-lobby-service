package ru.rsreu.jackal.api.lobby.http.gameCreationErrorHandler.dto

import ru.rsreu.jackal.api.lobby.websocket.WebSocketMessage
import ru.rsreu.jackal.api.lobby.websocket.WebSocketMessageType
import ru.rsreu.jackal.shared_models.requests.GameSessionCreationError

data class GameCreationErrorInfoResponse(val error: GameSessionCreationError) :
    WebSocketMessage(WebSocketMessageType.GAME_SESSION_CREATION_ERROR_INFO_FOR_ALL)