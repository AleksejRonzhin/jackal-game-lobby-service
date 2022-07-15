package ru.rsreu.jackal.websocket.dto

import ru.rsreu.jackal.models.LobbyMemberInfo

data class UserConnectedInfoForOneResponse(
    val type: WebSocketResponseType = WebSocketResponseType.CONNECTED_INFO_FOR_ONE,
    val usersInLobby: List<LobbyMemberInfo>,
    val hostId: Long
)
