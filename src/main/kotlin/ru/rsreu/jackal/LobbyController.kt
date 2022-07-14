package ru.rsreu.jackal

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.rsreu.jackal.shared_models.WebSocketInfo
import ru.rsreu.jackal.shared_models.requests.CreateLobbyRequest
import ru.rsreu.jackal.shared_models.responses.CreateLobbyResponse
import ru.rsreu.jackal.shared_models.responses.CreateLobbyStatus

@RestController
@RequestMapping("/api/lobby")
class LobbyController(
    private val service: LobbyService,
    private val jwtTokenProvider: JwtTokenProvider
    ) {
    @PostMapping("create")
    fun create(@RequestBody request: CreateLobbyRequest): ResponseEntity<CreateLobbyResponse> {
        val lobbyId = service.create(request.lobbyName, request.lobbyPassword)
        val userId = request.hostId
        val webSocketInfo = WebSocketInfo(lobbyId, userId)
        val hostToken = jwtTokenProvider.createAccessToken(lobbyId, userId)
        val response = CreateLobbyResponse(webSocketInfo, hostToken, CreateLobbyStatus.OK)
        return ResponseEntity.ok(response)
    }
}
