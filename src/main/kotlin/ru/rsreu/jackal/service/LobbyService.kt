package ru.rsreu.jackal.service

import org.springframework.stereotype.Service
import ru.rsreu.jackal.exception.*
import ru.rsreu.jackal.models.Lobby
import ru.rsreu.jackal.repository.LobbyRepository

@Service
class LobbyService(private val repository: LobbyRepository) {
    fun create(lobbyName: String, lobbyPassword: String? = null, hostId: Long): Long {
        checkLobbyByUserExistingOrThrow(hostId)
        return repository.createLobby(lobbyName, lobbyPassword, hostId)
    }

    private fun checkLobbyByUserExistingOrThrow(userId: Long) {
        if (repository.findByUser(userId) != null) {
            throw UserAlreadyInLobbyException()
        }
    }

    fun preConnect(lobbyTitle: String, lobbyPassword: String? = null, userId: Long): Long {
        checkLobbyByUserExistingOrThrow(userId)
        val lobby = repository.findLobbyByTitle(lobbyTitle) ?: throw LobbyNotFoundException()
        checkPasswordsOrThrow(lobby, lobbyPassword)
        checkInGameOrThrow(lobby)
        checkUserInBlackListOrThrow(lobby, userId)
        lobby.addUser(userId)
        return lobby.id
    }

    private fun checkUserInBlackListOrThrow(lobby: Lobby, userId: Long) {
        if (lobby.checkUserInBlackListById(userId)) {
            throw UserInBlackListException()
        }
    }

    private fun checkInGameOrThrow(lobby: Lobby) {
        if (lobby.isInGame) {
            throw LobbyInGameException()
        }
    }

    private fun checkPasswordsOrThrow(lobby: Lobby, lobbyPassword: String?) {
        if (lobby.password != null && lobby.password != lobbyPassword) {
            throw WrongLobbyPasswordException()
        }
    }
}
