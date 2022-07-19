package ru.rsreu.jackal.shared_models.responses

data class GetLobbyInfoForStartResponse(
    val lobbyId: Long? = null,
    val userIds: Collection<Long>? = null,
    val gameModeId: Long? = null,
    override val responseStatus: HttpLobbyResponseStatus
) : HttpLobbyResponse(responseStatus)
