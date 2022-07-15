package ru.rsreu.jackal.service

import org.springframework.stereotype.Service
import ru.rsreu.jackal.exception.*
import ru.rsreu.jackal.models.Lobby
import ru.rsreu.jackal.models.LobbyMemberInfo
import ru.rsreu.jackal.repository.LobbyRepository

@Service
class LobbyService(private val repository: LobbyRepository) {
    fun create(lobbyTitle: String, lobbyPassword: String? = null, hostId: Long): Long {
        checkLobbyByUserExistingOrThrow(hostId)
        runCatching { getLobbyByTitleOrThrow(lobbyTitle) }.onSuccess { throw NotUniqueLobbyTitleException() }
        return repository.createLobby(lobbyTitle, lobbyPassword, hostId)
    }

    private fun checkLobbyByUserExistingOrThrow(userId: Long) {
        if (repository.findLobbyByUser(userId) != null) {
            throw UserAlreadyInLobbyException()
        }
    }

    fun preConnect(lobbyTitle: String, lobbyPassword: String? = null, userId: Long): Long {
        checkLobbyByUserExistingOrThrow(userId)
        val lobby = getLobbyByTitleOrThrow(lobbyTitle)
        checkPasswordsOrThrow(lobby, lobbyPassword)
        checkInGameOrThrow(lobby)
        checkUserInBlackListOrThrow(lobby, userId)
        lobby.addUser(userId)
        return lobby.id
    }

    private fun getLobbyByTitleOrThrow(lobbyTitle: String) =
        repository.findLobbyByTitle(lobbyTitle) ?: throw LobbyNotFoundException()

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

    fun connectUser(userId: Long) {
        val lobby = repository.findLobbyByUser(userId) ?: throw LobbyNotFoundException(userId)
        lobby.connectUser(userId)
    }

    fun getAllLobbyMembers(lobbyId: Long): List<LobbyMemberInfo>? =
        repository.findLobbyById(lobbyId)?.getAllMembersIds()

}
