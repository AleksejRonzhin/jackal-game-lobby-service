package ru.rsreu.jackal.api.lobby.http.gameSessionCreationInfoSending

import org.springframework.stereotype.Component
import ru.rsreu.jackal.api.lobby.http.gameSessionCreationInfoSending.dto.GameSessionCreationInfoResponse
import ru.rsreu.jackal.api.lobby.websocket.WebSocketUtil
import java.util.*

@Component
class GameSessionCreationInfoSender(private val wsUtil: WebSocketUtil) {
    fun sendInfo(lobbyId: UUID) {
        wsUtil.sendInfoForLobby(lobbyId, GameSessionCreationInfoResponse())
    }
}