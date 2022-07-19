package ru.rsreu.jackal.api.lobby.websocket.status_changing.dto

import ru.rsreu.jackal.api.lobby.websocket.WebSocketMessage
import ru.rsreu.jackal.api.lobby.websocket.WebSocketMessageType
import ru.rsreu.jackal.shared_models.LobbyMemberInfo

data class UserChangedStatusInfoForAllResponse(
    val lobbyMemberInfo: LobbyMemberInfo
) : WebSocketMessage(WebSocketMessageType.CHANGED_STATE_INFO_FOR_ALL)