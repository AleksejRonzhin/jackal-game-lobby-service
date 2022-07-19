package ru.rsreu.jackal.api.lobby.http.lobbiesGetting.dto

import ru.rsreu.jackal.api.lobby.websocket.WebSocketMessage
import ru.rsreu.jackal.api.lobby.websocket.WebSocketMessageType
import ru.rsreu.jackal.shared_models.LobbyMemberInfo

data class LobbyNotReadyResponse(val notConnectedUsersIds: List<Long>, val usersInLobby: List<LobbyMemberInfo>) :
    WebSocketMessage(WebSocketMessageType.LOBBY_NOT_READY_INFO_FOR_ALL)