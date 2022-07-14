package ru.rsreu.jackal.shared_models.requests

data class PreConnectLobbyRequest(val lobbyTitle: String, val lobbyPassword: String?, val userId: Long)