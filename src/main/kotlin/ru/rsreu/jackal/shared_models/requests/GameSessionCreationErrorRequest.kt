package ru.rsreu.jackal.shared_models.requests

data class GameSessionCreationErrorRequest(val error: GameSessionCreationError)

enum class GameSessionCreationError {
    GAME_NOT_FOUND,
    USERS_IN_LOBBY_TO_MUCH,
    USERS_IN_LOBBY_TOO_SMALL,
    ENTERPRISE_FAIL,
    GAME_SERVICE_NOT_AVAILABLE,
    GAME_SERVICE_FAIL
}