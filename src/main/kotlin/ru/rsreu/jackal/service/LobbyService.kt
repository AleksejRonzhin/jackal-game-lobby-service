package ru.rsreu.jackal.service

import org.springframework.stereotype.Service
import ru.rsreu.jackal.exception.*
import ru.rsreu.jackal.models.Lobby
import ru.rsreu.jackal.shared_models.LobbyMemberInfo
import ru.rsreu.jackal.repository.LobbyRepository
import ru.rsreu.jackal.shared_models.LobbyInfo

@Service
class LobbyService(private val repository: LobbyRepository) {
    fun create(lobbyTitle: String, lobbyPassword: String? = null, hostId: Long): Long {
        checkHostIsNotInAnyLobbyOrThrow(hostId)
        runCatching { getLobbyByTitleOrThrow(lobbyTitle) }.onSuccess { throw NotUniqueLobbyTitleException() }
        return repository.createLobby(lobbyTitle, lobbyPassword, hostId)
    }

    private fun checkHostIsNotInAnyLobbyOrThrow(hostId: Long) {
        if (isUserInAnyLobby(hostId)) {
            throw HostAlreadyInLobbyException()
        }
    }

    private fun isUserInAnyLobby(userId: Long): Boolean = repository.findLobbyByUser(userId) != null

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

    fun connectUserAndGetHostIdAndAllMembers(userId: Long, lobbyId: Long): Pair<Long, List<LobbyMemberInfo>> {
        val lobby = getLobbyByIdOrThrow(lobbyId, LobbyNotFoundException(userId))
        lobby.connectUser(userId)
        return Pair(lobby.host!!.userId, lobby.getAllMembers())
    }

    fun changeGame(gameId: Long, userId: Long) {
        val lobby = getLobbyByUserIdOrThrow(userId)
        checkUserIsHostOrThrow(lobby, userId)
        lobby.gameId = gameId
    }

    fun getLobbyByUserIdOrThrow(userId: Long, exception: Throwable = UserNotInAnyLobbyException()): Lobby =
        repository.findLobbyByUser(userId) ?: throw exception

    private fun checkUserIsHostOrThrow(lobby: Lobby, userId: Long) {
        if (lobby.host!!.userId != userId) {
            throw UserIsNotHostException()
        }
    }

    fun disconnectUserAndGetHostId(userId: Long, lobbyId: Long): Long? {
        val lobby = getLobbyByIdOrThrow(lobbyId, LobbyNotFoundException(userId))
        lobby.disconnectUser(userId)
        val newHostId = lobby.host?.userId
        if (newHostId == null) {
            repository.removeLobbyById(lobbyId)
        }
        return newHostId
    }

    private fun getLobbyByIdOrThrow(lobbyId: Long, exception: Throwable) =
        repository.findLobbyById(lobbyId) ?: throw exception

    fun changeUserStateAndGetInfo(userId: Long, lobbyId: Long): LobbyMemberInfo {
        val lobby = getLobbyByIdOrThrow(lobbyId, LobbyNotFoundException(userId))
        return lobby.changeMemberStateAndGetInfo(userId)
    }

    fun kickUserFromLobby(hostId: Long, lobbyId: Long, kickableUserId: Long) {
        val lobby = getLobbyByIdOrThrow(lobbyId, LobbyNotFoundException(hostId))
        lobby.kickUser(hostId, kickableUserId)
    }

    fun getAllLobbiesInfo(): Collection<LobbyInfo> = repository.getAllLobbies().map { transferLobbyToLobbyInfo(it) }

    private fun transferLobbyToLobbyInfo(lobby: Lobby): LobbyInfo {
        return LobbyInfo(
            lobby.title,
            lobby.password == null,
            lobby.getAllMembers().map { transferLobbyMemberInfo(it) },
            lobby.host!!.userId,
            lobby.gameId
        )
    }

    private fun transferLobbyMemberInfo(lobbyMember: LobbyMemberInfo): LobbyMemberInfo {
        return LobbyMemberInfo(lobbyMember.userId, lobbyMember.status)
    }
}
