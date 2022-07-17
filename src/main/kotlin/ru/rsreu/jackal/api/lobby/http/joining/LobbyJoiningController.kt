package ru.rsreu.jackal.api.lobby.http.joining

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.rsreu.jackal.api.lobby.JwtTokenProvider
import ru.rsreu.jackal.api.lobby.service.LobbyService
import ru.rsreu.jackal.api.lobby.websocket.WebSocketInfoCreator
import ru.rsreu.jackal.shared_models.requests.JoinLobbyRequest
import ru.rsreu.jackal.shared_models.responses.HttpLobbyResponseStatus
import ru.rsreu.jackal.shared_models.responses.JoinLobbyResponse

@RestController
@RequestMapping("/api/lobby")
class LobbyJoiningController(
    private val lobbyService: LobbyService,
    private val webSocketInfoCreator: WebSocketInfoCreator,
    private val jwtTokenProvider: JwtTokenProvider
) {
    @PostMapping("/join")
    fun join(@RequestBody request: JoinLobbyRequest): ResponseEntity<JoinLobbyResponse> {
        val userId = request.userId
        val lobbyId = lobbyService.join(request.lobbyTitle, request.lobbyPassword, userId)
        val webSocketInfo = webSocketInfoCreator.of(lobbyId, userId)
        return ResponseEntity.ok(
            JoinLobbyResponse(
                webSocketInfo = webSocketInfo,
                token = jwtTokenProvider.createAccessToken(lobbyId, userId),
                responseStatus = HttpLobbyResponseStatus.OK
            )
        )
    }
}