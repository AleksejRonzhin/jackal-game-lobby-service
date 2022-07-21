package ru.rsreu.jackal.api.lobby.http.gameFinish

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import ru.rsreu.jackal.api.lobby.service.LobbyService
import ru.rsreu.jackal.shared_models.HttpResponse
import ru.rsreu.jackal.shared_models.HttpResponseStatus
import java.util.*

@RestController
@RequestMapping("/lobby")
class GameFinishController(
    private val lobbyService: LobbyService,
    private val sender: GameFinishSender
) {

    @PostMapping("/user-finish-game")
    fun userFinishGame(@RequestParam userId: Long): ResponseEntity<HttpResponse> {
        val (lobbyId, memberInfo) = lobbyService.userFinishGame(userId)
        sender.sendUserFinishGameForLobby(lobbyId, memberInfo)
        return ResponseEntity.ok(HttpResponse(HttpResponseStatus.OK))
    }

    @PostMapping("/finish-game")
    fun finishGame(@RequestParam lobbyId: UUID): ResponseEntity<HttpResponse> {
        val membersInfo = lobbyService.finishGame(lobbyId)
        sender.sendFinishGameForLobby(lobbyId, membersInfo)
        return ResponseEntity.ok(HttpResponse(HttpResponseStatus.OK))
    }
}