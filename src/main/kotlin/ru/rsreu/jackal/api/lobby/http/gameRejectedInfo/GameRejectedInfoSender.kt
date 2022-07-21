package ru.rsreu.jackal.api.lobby.http.gameRejectedInfo

import org.springframework.stereotype.Component
import ru.rsreu.jackal.api.lobby.http.gameRejectedInfo.dto.RejectedGameInfoResponse
import ru.rsreu.jackal.api.lobby.websocket.WebSocketUtil
import ru.rsreu.jackal.api.lobby.websocket.status_changing.dto.UserChangedStatusInfoForAllResponse
import ru.rsreu.jackal.shared_models.LobbyMemberInfo
import java.util.*

@Component
class GameRejectedInfoSender(private val wsUtil: WebSocketUtil) {
    fun sendGameRejectedInfoForLobby(lobbyId: UUID, notConnectedUsersIds: List<Long>) {
        wsUtil.sendInfoForLobby(lobbyId, RejectedGameInfoResponse(notConnectedUsersIds))
    }

    fun sendChangingStatusInfosForLobby(lobbyId: UUID, members: List<LobbyMemberInfo>) {
        members.forEach {
            wsUtil.sendInfoForLobby(lobbyId, UserChangedStatusInfoForAllResponse(it))
        }
    }
}