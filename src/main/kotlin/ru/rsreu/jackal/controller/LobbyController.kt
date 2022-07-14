package ru.rsreu.jackal.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.rsreu.jackal.jwt.JwtTokenProvider
import ru.rsreu.jackal.service.LobbyService
import ru.rsreu.jackal.shared_models.requests.CreateLobbyRequest
import ru.rsreu.jackal.shared_models.responses.CreateLobbyResponse
import ru.rsreu.jackal.shared_models.responses.CreateLobbyStatus

@RestController
@RequestMapping("/api/lobby")
class LobbyController(
    private val service: LobbyService,
    private val jwtTokenProvider: JwtTokenProvider,
    private val webSocketInfoCreator: WebSocketInfoCreator
) {
    @PostMapping("create")
    fun create(@RequestBody request: CreateLobbyRequest): ResponseEntity<CreateLobbyResponse> {
        val lobbyId = service.create(request.lobbyName, request.lobbyPassword, request.hostId)
        val userId = request.hostId
        val webSocketInfo = webSocketInfoCreator.of(lobbyId, userId)
        return ResponseEntity.ok(
            CreateLobbyResponse(
                webSocketInfo = webSocketInfo,
                hostToken = jwtTokenProvider.createAccessToken(lobbyId, userId),
                responseStatus = CreateLobbyStatus.OK
            )
        )
    }
}
