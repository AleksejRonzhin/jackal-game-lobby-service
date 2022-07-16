package ru.rsreu.jackal.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import ru.rsreu.jackal.jwt.JwtTokenProvider
import ru.rsreu.jackal.service.LobbyService
import ru.rsreu.jackal.shared_models.requests.ChangeGameRequest
import ru.rsreu.jackal.shared_models.requests.CreateLobbyRequest
import ru.rsreu.jackal.shared_models.requests.JoinLobbyRequest
import ru.rsreu.jackal.shared_models.responses.*

@RestController
@RequestMapping("/api/lobby")
class LobbyController(
    private val lobbyService: LobbyService,
    private val jwtTokenProvider: JwtTokenProvider,
    private val webSocketInfoCreator: WebSocketInfoCreator
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
                responseStatus = CreateLobbyStatus.OK
            )
        )
    }

    @PostMapping("/join")
    fun join(@RequestBody request: JoinLobbyRequest): ResponseEntity<JoinLobbyResponse> {
        val userId = request.userId
        val lobbyId = lobbyService.join(request.lobbyTitle, request.lobbyPassword, userId)
        val webSocketInfo = webSocketInfoCreator.of(lobbyId, userId)
        return ResponseEntity.ok(
            JoinLobbyResponse(
                webSocketInfo = webSocketInfo,
                token = jwtTokenProvider.createAccessToken(lobbyId, userId),
                responseStatus = JoinLobbyStatus.OK
            )
        )
    }

    @GetMapping("/connection-info/userId={userId}")
    fun getInfoAboutConnection(@PathVariable userId: Long): ResponseEntity<GetLobbyConnectionInfoResponse> {
        val lobbyId = lobbyService.getLobbyByUserIdOrThrow(userId).id
        val webSocketInfo = webSocketInfoCreator.of(lobbyId, userId)
        return ResponseEntity.ok(
            GetLobbyConnectionInfoResponse(
                webSocketInfo = webSocketInfo,
                token = jwtTokenProvider.createAccessToken(lobbyId, userId),
                responseStatus = GetLobbyConnectionInfoStatus.OK
            )
        )
    }

    @PostMapping("/change-game")
    fun changeGame(@RequestBody request: ChangeGameRequest): ResponseEntity<ChangeGameResponse> {
        lobbyService.changeGame(request.gameId, request.userId)
        return ResponseEntity.ok(
            ChangeGameResponse(responseStatus = ChangeGameStatus.OK)
        )
    }

    @GetMapping("/all")
    fun getAll(): ResponseEntity<GetAllLobbiesResponse> {
        val lobbies = lobbyService.getAllLobbiesInfo()
        return ResponseEntity.ok(GetAllLobbiesResponse(lobbies, responseStatus = GetAllLobbiesStatus.OK))
    }
}