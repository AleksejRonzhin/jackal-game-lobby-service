package ru.rsreu.jackal.websocket.connection.dto

import ru.rsreu.jackal.models.LobbyMember
import ru.rsreu.jackal.websocket.WebSocketResponseType

data class UserConnectedInfoForOneResponse(
    val type: WebSocketResponseType = WebSocketResponseType.CONNECTED_INFO_FOR_ONE,
    val usersInLobby: List<LobbyMember>,
    val hostId: Long
)
