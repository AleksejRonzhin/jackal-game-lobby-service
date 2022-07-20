package ru.rsreu.jackal.api.lobby.http.gameSessionConnectionInfoSending.dto

import ru.rsreu.jackal.api.lobby.websocket.WebSocketMessage
import ru.rsreu.jackal.api.lobby.websocket.WebSocketMessageType
import ru.rsreu.jackal.shared_models.WebSocketInfo

data class GameSessionConnectionInfoResponse(val webSocketInfo: WebSocketInfo, val jwt: String) :
    WebSocketMessage(WebSocketMessageType.GAME_SESSION_CONNECTION_INFO_FOR_ONE)