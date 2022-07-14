package ru.rsreu.jackal.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.rsreu.jackal.jwt.JwtTokenProvider
import ru.rsreu.jackal.service.LobbyService
import ru.rsreu.jackal.shared_models.requests.CreateLobbyRequest
import ru.rsreu.jackal.shared_models.requests.PreConnectLobbyRequest
import ru.rsreu.jackal.shared_models.responses.PreConnectLobbyResponse
import ru.rsreu.jackal.shared_models.responses.PreConnectLobbyStatus

@RestController
@RequestMapping("/api/lobby")
class LobbyController(
    private val service: LobbyService,
    private val jwtTokenProvider: JwtTokenProvider,
    private val webSocketInfoCreator: WebSocketInfoCreator
) {
    @PostMapping("/create")
    fun create(@RequestBody request: CreateLobbyRequest): ResponseEntity<PreConnectLobbyResponse> {
        val userId = request.hostId
        val lobbyId = service.create(request.lobbyTitle, request.lobbyPassword, userId)
        val webSocketInfo = webSocketInfoCreator.of(lobbyId, userId)
        return ResponseEntity.ok(
            PreConnectLobbyResponse(
                webSocketInfo = webSocketInfo,
                token = jwtTokenProvider.createAccessToken(lobbyId, userId),
                responseStatus = PreConnectLobbyStatus.OK
            )
        )
    }

    @PostMapping("/pre-connect")
    fun preConnect(@RequestBody request: PreConnectLobbyRequest): ResponseEntity<PreConnectLobbyResponse> {
        val userId = request.userId
        val lobbyId = service.preConnect(request.lobbyTitle, request.lobbyPassword, userId)
        val webSocketInfo = webSocketInfoCreator.of(lobbyId, userId)
        return ResponseEntity.ok(
            PreConnectLobbyResponse(
                webSocketInfo = webSocketInfo,
                token = jwtTokenProvider.createAccessToken(lobbyId, userId),
                responseStatus = PreConnectLobbyStatus.OK
            )
        )
    }
}
