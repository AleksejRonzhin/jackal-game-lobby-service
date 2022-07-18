package ru.rsreu.jackal.api.lobby.http.connectionInfoGetting

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import ru.rsreu.jackal.api.lobby.JwtTokenProvider
import ru.rsreu.jackal.api.lobby.service.LobbyService
import ru.rsreu.jackal.api.lobby.websocket.WebSocketInfoCreator
import ru.rsreu.jackal.shared_models.responses.GetLobbyConnectionInfoResponse
import ru.rsreu.jackal.shared_models.responses.HttpLobbyResponseStatus

@RestController
@RequestMapping("/api/lobby")
class LobbyConnectionInfoGettingController(
    private val lobbyService: LobbyService,
    private val jwtTokenProvider: JwtTokenProvider,
    private val webSocketInfoCreator: WebSocketInfoCreator
) {
    @GetMapping("/connection-info")
    fun getInfoAboutConnection(@RequestParam userId: Long): ResponseEntity<GetLobbyConnectionInfoResponse> {
        val lobbyId = lobbyService.getLobbyByUserIdOrThrow(userId).id
        val webSocketInfo = webSocketInfoCreator.of(lobbyId, userId)
        return ResponseEntity.ok(
            GetLobbyConnectionInfoResponse(
                webSocketInfo = webSocketInfo,
                token = jwtTokenProvider.createAccessToken(lobbyId, userId),
                responseStatus = HttpLobbyResponseStatus.OK
            )
        )
    }
}