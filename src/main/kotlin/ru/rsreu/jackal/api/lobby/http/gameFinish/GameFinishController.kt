package ru.rsreu.jackal.api.lobby.http.gameFinish

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import ru.rsreu.jackal.api.lobby.service.LobbyService

@RestController
@RequestMapping("/lobby")
class GameFinishController(
    private val lobbyService: LobbyService
) {

    @PostMapping("/user-finish-game")
    fun finishGame(@RequestParam userId: Long){
        lobbyService.finishGame(userId)
    }
}