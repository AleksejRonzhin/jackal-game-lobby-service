package ru.rsreu.jackal.models

import ru.rsreu.jackal.exception.*

class Lobby(
    val id: Long,
    val title: String,
    var password: String? = null,
    host: LobbyMemberInfo,
    var gameId: Long? = null,
    var isInGame: Boolean = false,
) {
    private val members: MutableCollection<LobbyMemberInfo> = mutableListOf()

    private val blackList: MutableList<Long> = mutableListOf()

    var host: LobbyMemberInfo? = host
        private set

    init {
        members.add(host)
    }

    fun addToBlackList(id: Long) = blackList.add(id)

    fun checkUserInBlackListById(id: Long) = blackList.contains(id)

    fun checkUserInLobbyById(userId: Long) = members.find { it.userId == userId } != null

    fun addUser(userId: Long) = members.add(LobbyMemberInfo(userId))

    fun connectUser(userId: Long) {
        val member =
            members.find { it.userId == userId } ?: throw LobbyNotFoundForUserConnectionInfoException(userId)
        member.status =
            if (member.status == LobbyMemberStatus.NOT_CONNECTED) LobbyMemberStatus.NOT_READY else member.status
    }

    fun getAllMembers() = members.toList()

    fun disconnectUser(userId: Long) {
        val member = getMemberOrThrow(userId)
        if (member.status == LobbyMemberStatus.IN_GAME) {
            throw AttemptToLeaveFromLobbyInGameException(userId)
        }
        members.remove(member)
        if (host?.userId == userId) {
            setNewRandomHost()
        }
    }

    private fun getMemberOrThrow(userId: Long) =
        members.find { it.userId == userId } ?: throw UserNotFoundInAnyLobbyException(userId)

    private fun setNewRandomHost() {
        host = members.randomOrNull()
    }

    fun changeMemberStateAndGetInfo(userId: Long): LobbyMemberInfo {
        val member = getMemberOrThrow(userId)
        if (member.status == LobbyMemberStatus.IN_GAME) {
            throw AttemptToChangeStateInGameException(userId)
        }
        if (member.status == LobbyMemberStatus.NOT_CONNECTED) {
            throw AttemptToChangeStateNotConnectedException(userId)
        }
        member.status =
            if (member.status == LobbyMemberStatus.READY) LobbyMemberStatus.NOT_READY else LobbyMemberStatus.READY
        return member
    }
}