package ru.rsreu.jackal.api.lobby.http.gameFinish

import org.springframework.stereotype.Component
import ru.rsreu.jackal.api.lobby.websocket.WebSocketUtil
import ru.rsreu.jackal.api.lobby.websocket.status_changing.dto.UserChangedStatusInfoForAllResponse
import ru.rsreu.jackal.shared_models.LobbyMemberInfo
import java.util.*

@Component
class GameFinishSender(private val wsUtil: WebSocketUtil) {

    fun sendUserFinishGameForLobby( lobbyId: UUID, memberInfo: LobbyMemberInfo) {
        wsUtil.sendInfoForLobby(lobbyId, UserChangedStatusInfoForAllResponse(memberInfo))
    }

    fun sendFinishGameForLobby(lobbyId: UUID, members: Collection<LobbyMemberInfo>){
        members.forEach {
            wsUtil.sendInfoForLobby(lobbyId, UserChangedStatusInfoForAllResponse(it))
        }
    }
}