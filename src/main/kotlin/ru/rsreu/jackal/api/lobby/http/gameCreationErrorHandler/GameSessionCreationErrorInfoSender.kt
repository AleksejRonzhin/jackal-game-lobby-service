package ru.rsreu.jackal.api.lobby.http.gameCreationErrorHandler

import org.springframework.stereotype.Component
import ru.rsreu.jackal.api.lobby.http.gameCreationErrorHandler.dto.GameCreationErrorInfoResponse
import ru.rsreu.jackal.api.lobby.websocket.WebSocketUtil
import ru.rsreu.jackal.shared_models.requests.GameSessionCreationError
import java.util.*

@Component
class GameSessionCreationErrorInfoSender(private val wsUtil: WebSocketUtil) {
    fun sendErrorInfoForAll(lobbyId: UUID, error: GameSessionCreationError) {
        wsUtil.sendInfoForLobby(lobbyId, GameCreationErrorInfoResponse(error))
    }
}