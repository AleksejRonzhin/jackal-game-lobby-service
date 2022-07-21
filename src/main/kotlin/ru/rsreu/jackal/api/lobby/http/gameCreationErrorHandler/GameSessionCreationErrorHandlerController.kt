package ru.rsreu.jackal.api.lobby.http.gameCreationErrorHandler

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.rsreu.jackal.shared_models.requests.GameSessionCreationErrorRequest

@RestController
@RequestMapping("/api/lobby")
class GameSessionCreationErrorHandlerController {
    @PostMapping("/send-creation-error-info")
    fun sendCreationErrorInfoToLobby(@RequestBody request: GameSessionCreationErrorRequest) {

    }
}