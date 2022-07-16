package ru.rsreu.jackal.websocket.connection.dto

import ru.rsreu.jackal.shared_models.LobbyMemberInfo
import ru.rsreu.jackal.websocket.WebSocketResponseType

data class UserConnectedInfoForOneResponse(
    val type: WebSocketResponseType = WebSocketResponseType.CONNECTED_INFO_FOR_ONE,
    val usersInLobby: List<LobbyMemberInfo>,
    val hostId: Long
)
