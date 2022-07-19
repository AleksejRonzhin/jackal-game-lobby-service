package ru.rsreu.jackal.api.lobby.http.creating

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.rsreu.jackal.api.lobby.JwtTokenProvider
import ru.rsreu.jackal.api.lobby.service.LobbyService
import ru.rsreu.jackal.api.lobby.websocket.WebSocketInfoCreator
import ru.rsreu.jackal.shared_models.HttpResponseStatus
import ru.rsreu.jackal.shared_models.requests.CreateLobbyRequest
import ru.rsreu.jackal.shared_models.responses.CreateResponse

@RestController
@RequestMapping("/api/lobby")
class LobbyCreatingController(
    private val lobbyService: LobbyService,
    private val webSocketInfoCreator: WebSocketInfoCreator,
    private val jwtTokenProvider: JwtTokenProvider
) {
    @PostMapping("/create")
    fun create(@RequestBody request: CreateLobbyRequest): ResponseEntity<CreateResponse> {
        val userId = request.hostId
        val lobby = lobbyService.create(request.lobbyTitle, request.lobbyPassword, userId)
        val webSocketInfo = webSocketInfoCreator.of(lobby.id, userId)
        return ResponseEntity.ok(
            CreateResponse(
                webSocketInfo = webSocketInfo,
                token = jwtTokenProvider.createAccessToken(lobby.id, userId),
                responseStatus = HttpResponseStatus.OK
            )
        )
    }
}