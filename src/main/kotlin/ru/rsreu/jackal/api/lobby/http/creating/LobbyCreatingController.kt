package ru.rsreu.jackal.api.lobby.http.creating

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.rsreu.jackal.api.lobby.JwtTokenProvider
import ru.rsreu.jackal.api.lobby.service.LobbyService
import ru.rsreu.jackal.api.lobby.websocket.WebSocketInfoCreator
import ru.rsreu.jackal.shared_models.requests.CreateLobbyRequest
import ru.rsreu.jackal.shared_models.responses.CreateLobbyResponse
import ru.rsreu.jackal.shared_models.responses.HttpLobbyResponseStatus

@RestController
@RequestMapping("/api/lobby")
class LobbyCreatingController(
    private val lobbyService: LobbyService,
    private val webSocketInfoCreator: WebSocketInfoCreator,
    private val jwtTokenProvider: JwtTokenProvider
) {
    @PostMapping("/create")
    fun create(@RequestBody request: CreateLobbyRequest): ResponseEntity<CreateLobbyResponse> {
        val userId = request.hostId
        val lobbyId = lobbyService.create(request.lobbyTitle, request.lobbyPassword, userId)
        val webSocketInfo = webSocketInfoCreator.of(lobbyId, userId)
        return ResponseEntity.ok(
            CreateLobbyResponse(
                webSocketInfo = webSocketInfo,
                token = jwtTokenProvider.createAccessToken(lobbyId, userId),
                responseStatus = HttpLobbyResponseStatus.OK
            )
        )
    }
}