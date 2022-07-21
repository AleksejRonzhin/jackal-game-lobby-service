package ru.rsreu.jackal.api.lobby.http.gameRejectedInfo

import org.springframework.stereotype.Component
import ru.rsreu.jackal.api.lobby.http.gameRejectedInfo.dto.RejectedGameInfoResponse
import ru.rsreu.jackal.api.lobby.websocket.WebSocketUtil
import java.util.*

@Component
class GameRejectedInfoSender(private val wsUtil: WebSocketUtil) {
    fun sendGameRejectedInfoForLobby(lobbyId: UUID, notConnectedUsersIds: List<Long>) {
        wsUtil.sendInfoForLobby(lobbyId, RejectedGameInfoResponse(notConnectedUsersIds))
    }
}