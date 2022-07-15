package ru.rsreu.jackal.service

import org.springframework.stereotype.Service
import ru.rsreu.jackal.exception.*
import ru.rsreu.jackal.models.Lobby
import ru.rsreu.jackal.models.LobbyMemberInfo
import ru.rsreu.jackal.repository.LobbyRepository

@Service
class LobbyService(private val repository: LobbyRepository) {
    fun create(lobbyTitle: String, lobbyPassword: String? = null, hostId: Long): Long {
        checkHostIsNotInAnyLobbyOrThrow(hostId)
        runCatching { getLobbyByTitleOrThrow(lobbyTitle) }.onSuccess { throw NotUniqueLobbyTitleException() }
        return repository.createLobby(lobbyTitle, lobbyPassword, hostId)
    }


    private fun checkLobbyByUserExistingOrThrow(userId: Long) {
        if (repository.findLobbyByUser(userId) != null) {
            throw UserAlreadyInLobbyException()
        }
    }

    private fun checkHostIsNotInAnyLobbyOrThrow(hostId: Long) {
        if (isUserInAnyLobby(hostId)) {
            throw HostAlreadyInLobbyException()

        }
    }

    private fun isUserInAnyLobby(userId: Long): Boolean = repository.findByUser(userId) != null

    fun join(lobbyTitle: String, lobbyPassword: String? = null, userId: Long): Long {
        checkLobbyByUserExistingOrThrow(userId)
        val lobby = getLobbyByTitleOrThrow(lobbyTitle)
        checkPasswordsOrThrow(lobby, lobbyPassword)
        checkUserNotInBlackListOrThrow(lobby, userId)
        checkLobbyIsNotInGameOrThrow(lobby)
        lobby.addUser(userId)
        return lobby.id
    }

    private fun checkLobbyByUserExistingOrThrow(userId: Long) {
        if (isUserInAnyLobby(userId)) {
            throw UserAlreadyInLobbyException()
        }
    }

    private fun getLobbyByTitleOrThrow(lobbyTitle: String) =
        repository.findLobbyByTitle(lobbyTitle) ?: throw LobbyNotFoundException()

    private fun checkUserNotInBlackListOrThrow(lobby: Lobby, userId: Long) {
        if (lobby.checkUserInBlackListById(userId)) {
            throw UserInBlackListException()
        }
    }

    private fun checkLobbyIsNotInGameOrThrow(lobby: Lobby) {
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


    fun getByUserIdOrThrow(userId: Long): Lobby = repository.findByUser(userId) ?: throw UserNotInAnyLobbyException()

    fun changeGame(gameId: Long, userId: Long) {
        val lobby = getByUserIdOrThrow(userId)
        checkUserIsHostOrThrow(lobby, userId)
        lobby.gameId = gameId
    }

    fun checkUserIsHostOrThrow(lobby: Lobby, userId: Long){
        if(lobby.host.userId != userId){
            throw UserIsNotHostException()
        }
    }
}
