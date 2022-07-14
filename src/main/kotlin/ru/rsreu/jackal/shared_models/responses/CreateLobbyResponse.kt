package ru.rsreu.jackal.shared_models.responses

import ru.rsreu.jackal.shared_models.ResponseBody
import ru.rsreu.jackal.shared_models.WebSocketInfo

data class CreateLobbyResponse(
    val webSocketInfo: WebSocketInfo? = null,
    val hostToken: String? = null,
    override val responseStatus: CreateLobbyStatus
) : ResponseBody<CreateLobbyStatus>

enum class CreateLobbyStatus {
    OK, USER_ALREADY_IN_LOBBY
}
