package ru.rsreu.jackal.api.lobby.http.joining

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.rsreu.jackal.api.lobby.JwtTokenProvider
import ru.rsreu.jackal.api.lobby.service.LobbyService
import ru.rsreu.jackal.api.lobby.websocket.WebSocketInfoCreator
import ru.rsreu.jackal.shared_models.HttpResponseStatus
import ru.rsreu.jackal.shared_models.requests.JoinLobbyRequest
import ru.rsreu.jackal.shared_models.responses.JoinResponse

@RestController
@RequestMapping("/api/lobby")
class LobbyJoiningController(
    private val lobbyService: LobbyService,
    private val webSocketInfoCreator: WebSocketInfoCreator,
    private val jwtTokenProvider: JwtTokenProvider
) {
    @PostMapping("/join")
    fun join(@RequestBody request: JoinLobbyRequest): ResponseEntity<JoinResponse> {
        val userId = request.userId
        val lobby = lobbyService.join(request.lobbyTitle, request.lobbyPassword, userId)
        val webSocketInfo = webSocketInfoCreator.of(lobby.id, userId)
        return ResponseEntity.ok(
            JoinResponse(
                webSocketInfo = webSocketInfo,
                token = jwtTokenProvider.createAccessToken(lobby.id, userId),
                responseStatus = HttpResponseStatus.OK
            )
        )
    }
}