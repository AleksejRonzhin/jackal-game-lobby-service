package ru.rsreu.jackal.api.lobby.websocket.connection.dto

import ru.rsreu.jackal.api.lobby.websocket.WebSocketMessage
import ru.rsreu.jackal.api.lobby.websocket.WebSocketMessageType
import ru.rsreu.jackal.shared_models.LobbyMemberInfo

data class UserConnectedInfoForOneResponse(
    val usersInLobby: List<LobbyMemberInfo>,
    val hostId: Long
) : WebSocketMessage(WebSocketMessageType.CONNECTED_INFO_FOR_ONE)
