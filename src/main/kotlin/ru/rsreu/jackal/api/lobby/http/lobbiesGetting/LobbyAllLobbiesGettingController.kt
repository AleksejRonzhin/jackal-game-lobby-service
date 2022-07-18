package ru.rsreu.jackal.api.lobby.http.lobbiesGetting

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.rsreu.jackal.api.lobby.service.LobbyService
import ru.rsreu.jackal.shared_models.responses.GetAllLobbiesResponse
import ru.rsreu.jackal.shared_models.responses.HttpLobbyResponseStatus

@RestController
@RequestMapping("/api/lobby")
class LobbyAllLobbiesGettingController(
    private val lobbyService: LobbyService
) {
    @GetMapping("/all")
    fun getAll(): ResponseEntity<GetAllLobbiesResponse> {
        val lobbies = lobbyService.getAllLobbiesInfo()
        return ResponseEntity.ok(GetAllLobbiesResponse(lobbies, responseStatus = HttpLobbyResponseStatus.OK))
    }
}