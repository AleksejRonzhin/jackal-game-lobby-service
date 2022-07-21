package ru.rsreu.jackal.api.lobby.http.gameSessionCreationInfoSending.dto

import ru.rsreu.jackal.api.lobby.websocket.WebSocketMessage
import ru.rsreu.jackal.api.lobby.websocket.WebSocketMessageType

class GameSessionCreationInfoResponse :
    WebSocketMessage(WebSocketMessageType.GAME_SESSION_CREATION_INFO_FOR_ALL)