package ru.rsreu.jackal

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.rsreu.jackal.models.Lobby
import ru.rsreu.jackal.models.WebSocketInfo

@RestController
@RequestMapping("/api/lobby")
class LobbyController(private val service: LobbyService) {
    @PostMapping("create")
    fun create(@RequestBody request: CreateLobbyRequest): CreateLobbyResponse{
        println(request)
        return CreateLobbyResponse(WebSocketInfo(), "hostTOken", "key", CreateLobbyStatus.OK)
    }
}

class WebSocketInfo {
}

data class CreateLobbyRequest(
    val lobbyName: String,
    val isPrivateLobby: Boolean,
    val lobbyPassword: String?,
    var hostId: Long?
)

interface ResponseBody<T: Enum<T>> {
    val responseStatus: T
}

data class CreateLobbyResponse(
    val webSocketInfo: WebSocketInfo,
    val hostToken: String,
    val lobbySecretKey: String?,
    override val responseStatus: CreateLobbyStatus
) : ResponseBody<CreateLobbyStatus>

enum class CreateLobbyStatus {
    OK, FAIL
}
