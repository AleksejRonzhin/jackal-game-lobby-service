package ru.rsreu.jackal.api.lobby.http.gameCreationErrorHandler

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.rsreu.jackal.api.lobby.service.LobbyService
import ru.rsreu.jackal.shared_models.HttpResponse
import ru.rsreu.jackal.shared_models.HttpResponseStatus
import ru.rsreu.jackal.shared_models.requests.GameSessionCreationErrorRequest

@RestController
@RequestMapping("/api/lobby")
class GameSessionCreationErrorHandlerController(
    private val sender: GameSessionCreationErrorInfoSender,
    private val lobbyService: LobbyService
) {
    @PostMapping("/send-creation-error-info")
    fun sendCreationErrorInfoToLobby(@RequestBody request: GameSessionCreationErrorRequest): ResponseEntity<HttpResponse> {
        sender.sendErrorInfoForAll(request.lobbyId, request.error)
        lobbyService.rollbackLobby(request.lobbyId)
        return ResponseEntity.ok(HttpResponse(HttpResponseStatus.OK))
    }
}