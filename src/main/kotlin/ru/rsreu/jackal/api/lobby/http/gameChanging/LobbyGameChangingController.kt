package ru.rsreu.jackal.api.lobby.http.gameChanging

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.rsreu.jackal.api.lobby.service.LobbyService
import ru.rsreu.jackal.shared_models.requests.ChangeGameRequest
import ru.rsreu.jackal.shared_models.responses.ChangeGameResponse
import ru.rsreu.jackal.shared_models.responses.HttpLobbyResponseStatus

@RestController
@RequestMapping("/api/lobby")
class LobbyGameChangingController(
    private val lobbyService: LobbyService
) {
    @PostMapping("/change-game")
    fun changeGame(@RequestBody request: ChangeGameRequest): ResponseEntity<ChangeGameResponse> {
        lobbyService.changeGame(request.gameModeId, request.userId)
        return ResponseEntity.ok(
            ChangeGameResponse(responseStatus = HttpLobbyResponseStatus.OK)
        )
    }
}