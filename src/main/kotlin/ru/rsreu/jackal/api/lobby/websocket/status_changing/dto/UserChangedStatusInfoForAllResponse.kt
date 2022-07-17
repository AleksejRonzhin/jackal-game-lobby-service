package ru.rsreu.jackal.api.lobby.websocket.status_changing.dto

import ru.rsreu.jackal.api.lobby.websocket.WebSocketResponseType
import ru.rsreu.jackal.shared_models.LobbyMemberInfo

data class UserChangedStatusInfoForAllResponse(
    val type: WebSocketResponseType = WebSocketResponseType.CHANGED_STATE_INFO_FOR_ALL,
    val lobbyMemberInfo: LobbyMemberInfo
)