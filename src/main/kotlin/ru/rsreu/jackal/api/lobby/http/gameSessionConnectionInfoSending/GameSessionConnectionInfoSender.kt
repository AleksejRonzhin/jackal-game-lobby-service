package ru.rsreu.jackal.api.lobby.http.gameSessionConnectionInfoSending

import org.springframework.stereotype.Component
import ru.rsreu.jackal.api.lobby.http.gameSessionConnectionInfoSending.dto.GameSessionConnectionInfoResponse
import ru.rsreu.jackal.api.lobby.websocket.WebSocketUtil
import ru.rsreu.jackal.shared_models.responses.PlayerInfo

@Component
class GameSessionConnectionInfoSender(private val wsUtil: WebSocketUtil) {
    fun sendInfo(infos: Collection<PlayerInfo>) {
        infos.forEach {
            wsUtil.sendInfoForOne(it.userId, GameSessionConnectionInfoResponse(it.webSocketInfo, it.jwt))
        }
    }
}