package ru.rsreu.jackal.api.lobby.http.lobbiesGetting

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import ru.rsreu.jackal.api.lobby.service.LobbyService
import ru.rsreu.jackal.shared_models.HttpResponseStatus
import ru.rsreu.jackal.shared_models.responses.GetAllLobbiesResponse
import ru.rsreu.jackal.shared_models.responses.GetInfoForStartResponse

@RestController
@RequestMapping("/api/lobby")
class LobbyInfoController(
    private val lobbyService: LobbyService
) {
    @GetMapping("/all")
    fun getAll(): ResponseEntity<GetAllLobbiesResponse> {
        val lobbies = lobbyService.getAllLobbiesInfo()
        return ResponseEntity.ok(GetAllLobbiesResponse(lobbies, responseStatus = HttpResponseStatus.OK))
    }

    @GetMapping("/info-for-start")
    fun getInfoForStart(@RequestParam userId: Long): ResponseEntity<GetInfoForStartResponse> {
        val lobby = lobbyService.getLobbyByUserIdOrThrow(userId)
        lobbyService.checkUserIsHostOrThrow(lobby, userId)
        lobbyService.checkGameIsSelectedOrThrow(lobby)
        lobbyService.checkLobbyIsReadyForStart(lobby)
        lobbyService.checkConnection(lobby)
        return ResponseEntity.ok(
            GetInfoForStartResponse(
                lobbyId = lobby.id,
                userIds = lobby.getAllMembers().map { it.userId },
                gameModeId = lobby.gameModeId,
                responseStatus = HttpResponseStatus.OK
            )
        )
    }
}