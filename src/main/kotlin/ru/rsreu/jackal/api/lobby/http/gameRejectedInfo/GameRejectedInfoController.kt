package ru.rsreu.jackal.api.lobby.http.gameRejectedInfo

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.rsreu.jackal.shared_models.HttpResponse
import ru.rsreu.jackal.shared_models.HttpResponseStatus
import ru.rsreu.jackal.shared_models.requests.GameNotStartedRequest
import java.util.*

@RestController
@RequestMapping("/api/lobby")
class GameRejectedInfoController(val sender: GameRejectedInfoSender) {
    @PostMapping("/send-rejected-game-info")
    fun sendRejectedGameInfoToLobby(@RequestBody request: GameNotStartedRequest): ResponseEntity<HttpResponse> {
        sender.sendGameRejectedInfoForLobby(UUID.fromString(request.lobbyId), request.notConnectedUserIds)
        return ResponseEntity.ok(HttpResponse(HttpResponseStatus.OK))
    }
}