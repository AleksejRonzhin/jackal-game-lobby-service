package ru.rsreu.jackal.websocket.dto

import ru.rsreu.jackal.models.LobbyMemberInfo

data class UserConnectedInfoForOneResponse(val type: WebSocketResponseType, val usersInLobby: List<LobbyMemberInfo>)
