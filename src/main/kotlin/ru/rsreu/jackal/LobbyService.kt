package ru.rsreu.jackal

import org.springframework.stereotype.Service

@Service
class LobbyService(private val repository: LobbyRepository) {
    fun create(lobbyName: String, lobbyPassword: String?): Long {
        // проверка что он не в лобби

        return repository.createLobby()
    }
}
