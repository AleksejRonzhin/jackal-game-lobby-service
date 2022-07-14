package ru.rsreu.jackal.shared_models.requests

data class CreateLobbyRequest(
    val lobbyName: String,
    val lobbyPassword: String?,
    var hostId: Long
)
