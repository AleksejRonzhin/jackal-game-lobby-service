package ru.rsreu.jackal.api.lobby.service

import org.springframework.stereotype.Service
import ru.rsreu.jackal.api.lobby.Lobby
import ru.rsreu.jackal.api.lobby.repository.LobbyRepository
import ru.rsreu.jackal.exception.*
import ru.rsreu.jackal.shared_models.LobbyInfo
import ru.rsreu.jackal.shared_models.LobbyMemberInfo
import ru.rsreu.jackal.shared_models.LobbyMemberStatus

@Service
class LobbyService(private val repository: LobbyRepository) {
    fun create(lobbyTitle: String, lobbyPassword: String? = null, hostId: Long): Long {
        checkUserNotInAnyLobbyOrThrow(hostId)
        checkTitleIsUniqueOrThrow(lobbyTitle)
        return repository.createLobby(lobbyTitle, lobbyPassword, hostId)
    }

    private fun checkUserNotInAnyLobbyOrThrow(hostId: Long) {
        if (isUserInAnyLobby(hostId)) {
            throw UserAlreadyInLobbyException()
        }
    }

    private fun isUserInAnyLobby(userId: Long): Boolean = repository.findLobbyByUser(userId) != null

    private fun checkTitleIsUniqueOrThrow(lobbyTitle: String) {
        runCatching { getLobbyByTitleOrThrow(lobbyTitle) }.onSuccess { throw NotUniqueLobbyTitleException() }
    }

    fun join(lobbyTitle: String, lobbyPassword: String? = null, userId: Long): Long {
        checkUserNotInAnyLobbyOrThrow(userId)
        val lobby = getLobbyByTitleOrThrow(lobbyTitle)
        checkPasswordsOrThrow(lobby, lobbyPassword)
        checkUserNotInBlackListOrThrow(lobby, userId)
        checkLobbyNotInGameOrThrow(lobby)
        lobby.addUser(userId)
        return lobby.id
    }

    private fun getLobbyByTitleOrThrow(lobbyTitle: String) =
        repository.findLobbyByTitle(lobbyTitle) ?: throw LobbyNotFoundException()

    private fun checkUserNotInBlackListOrThrow(lobby: Lobby, userId: Long) {
        if (lobby.checkUserInBlackListById(userId)) {
            throw UserInBlackListException()
        }
    }

    private fun checkLobbyNotInGameOrThrow(lobby: Lobby) {
        if (lobby.isInGame) {
            throw LobbyInGameException()
        }
    }

    private fun checkPasswordsOrThrow(lobby: Lobby, lobbyPassword: String?) {
        if (lobby.password != null && lobby.password != lobbyPassword) {
            throw WrongLobbyPasswordException()
        }
    }

    fun changeGame(gameModeId: Long, userId: Long) {
        val lobby = getLobbyByUserIdOrThrow(userId)
        checkUserIsHostOrThrow(lobby, userId)
        checkLobbyNotInGameOrThrow(lobby)
        lobby.gameModeId = gameModeId
    }

    fun checkUserIsHostOrThrow(lobby: Lobby, userId: Long) {
        if (lobby.host!!.userId != userId) {
            throw UserNotIsHostException()
        }
    }

    fun getAllLobbiesInfo(): Collection<LobbyInfo> = repository.getAllLobbies().map { transferLobbyToLobbyInfo(it) }

    private fun transferLobbyToLobbyInfo(lobby: Lobby): LobbyInfo {
        return LobbyInfo(
            lobby.title,
            lobby.password == null,
            lobby.getAllMembers().map { transferLobbyMemberInfo(it) },
            lobby.host!!.userId,
            lobby.gameModeId
        )
    }

    private fun transferLobbyMemberInfo(lobbyMember: LobbyMemberInfo): LobbyMemberInfo {
        return LobbyMemberInfo(lobbyMember.userId, lobbyMember.status)
    }

    fun connectUserAndGetHostIdAndAllMembers(userId: Long, lobbyId: Long): Pair<Long, List<LobbyMemberInfo>> {
        val lobby = getLobbyByIdOrThrow(lobbyId, LobbyNotFoundException(userId))
        lobby.connectUser(userId)
        return Pair(lobby.host!!.userId, lobby.getAllMembers())
    }

    fun getLobbyByUserIdOrThrow(userId: Long, exception: Throwable = UserNotInLobbyException()): Lobby =
        repository.findLobbyByUser(userId) ?: throw exception


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

    fun checkLobbyIsReadyForStart(lobby: Lobby) {
        checkLobbyNotInGameOrThrow(lobby)
        checkAllMembersAreReadyOrThrow(lobby)
    }

    private fun checkAllMembersAreReadyOrThrow(lobby: Lobby) {
        //TODO ping all
        lobby.getAllMembers().forEach {
            if (it.status != LobbyMemberStatus.READY) {
                throw LobbyMemberNotReadyException()
            }
        }
    }
}
