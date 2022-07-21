package ru.rsreu.jackal.api.lobby.http.gameRejectedInfo.dto

import ru.rsreu.jackal.api.lobby.websocket.WebSocketMessage
import ru.rsreu.jackal.api.lobby.websocket.WebSocketMessageType

data class RejectedGameInfoResponse(val notConnectedUsersIds: Collection<Long>) :
    WebSocketMessage(WebSocketMessageType.REJECTED_GAME_INFO_FOR_ALL)