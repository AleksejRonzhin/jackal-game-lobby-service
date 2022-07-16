package ru.rsreu.jackal.websocket.status_changing.dto

import ru.rsreu.jackal.models.LobbyMember
import ru.rsreu.jackal.websocket.WebSocketResponseType

data class UserChangedStatusInfoForAllResponse(
    val type: WebSocketResponseType = WebSocketResponseType.CHANGED_STATE_INFO_FOR_ALL,
    val lobbyMemberInfo: LobbyMember
)