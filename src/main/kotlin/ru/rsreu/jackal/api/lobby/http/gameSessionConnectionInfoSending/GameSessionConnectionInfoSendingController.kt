package ru.rsreu.jackal.api.lobby.http.gameSessionConnectionInfoSending

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.rsreu.jackal.shared_models.HttpResponse
import ru.rsreu.jackal.shared_models.HttpResponseStatus
import ru.rsreu.jackal.shared_models.requests.SendGameSessionConnectionInfoRequest

@RestController
@RequestMapping("/api/lobby")
class GameSessionConnectionInfoSendingController(private val sender: GameSessionConnectionInfoSender) {

    @PostMapping("/send-game-session-connection-info")
    fun sendConnectionInfoToUsers(@RequestBody request: SendGameSessionConnectionInfoRequest)
            : ResponseEntity<HttpResponse> {
        sender.sendInfo(request.playerInfos)
        return ResponseEntity.ok(HttpResponse(HttpResponseStatus.OK))
    }
}
