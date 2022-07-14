package ru.rsreu.jackal.service

import org.springframework.stereotype.Service
import ru.rsreu.jackal.exception.UserAlreadyInLobbyException
import ru.rsreu.jackal.repository.LobbyRepository

@Service
class LobbyService(private val repository: LobbyRepository) {
    fun create(lobbyName: String, lobbyPassword: String? = null, hostId: Long): Long {
        if (repository.findByUser(hostId) != null) {
            throw UserAlreadyInLobbyException()
        }
        return repository.createLobby(lobbyName, lobbyPassword, hostId)
    }
}
