package ru.rsreu.jackal.models

import ru.rsreu.jackal.exception.*
import ru.rsreu.jackal.shared_models.LobbyMemberStatus

class Lobby(
    val id: Long,
    val title: String,
    var password: String? = null,
    host: LobbyMember,
    var gameId: Long? = null,
    var isInGame: Boolean = false,
) {
    private val members: MutableCollection<LobbyMember> = mutableListOf()

    private val blackList: MutableList<Long> = mutableListOf()

    var host: LobbyMember? = host
        private set

    init {
        members.add(host)
    }

    fun checkUserInBlackListById(id: Long) = blackList.contains(id)

    fun checkUserInLobbyById(userId: Long) = members.find { it.userId == userId } != null

    fun addUser(userId: Long) = members.add(LobbyMember(userId))

    fun connectUser(userId: Long) {
        val member = members.find { it.userId == userId } ?: throw LobbyNotFoundForUserConnectionInfoException(userId)
        member.status =
            if (member.status == LobbyMemberStatus.NOT_CONNECTED) LobbyMemberStatus.NOT_READY else member.status
    }

    fun getAllMembers() = members.toList()

    fun disconnectUser(userId: Long) {
        val member = getMemberOrThrow(userId, UserNotFoundInAnyLobbyException(userId))
        if (member.status == LobbyMemberStatus.IN_GAME) {
            throw AttemptToLeaveFromLobbyInGameException(userId)
        }
        members.remove(member)
        if (host?.userId == userId) {
            setNewRandomHost()
        }
    }

    private fun getMemberOrThrow(userId: Long, exception: Throwable) =
        members.find { it.userId == userId } ?: throw exception

    private fun setNewRandomHost() {
        host = members.randomOrNull()
    }

    fun changeMemberStateAndGetInfo(userId: Long): LobbyMember {
        val member = getMemberOrThrow(userId, UserNotFoundInAnyLobbyException(userId))
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

    fun kickUser(hostId: Long, kickableUserId: Long) {
        if (host!!.userId != hostId) {
            throw NotHostKickAttemptException(hostId)
        }
        if (host!!.userId == kickableUserId) {
            throw HostSelfKickAttemptException(hostId)
        }
        val memberToKick = getMemberOrThrow(kickableUserId, KickableUserNotInLobbyException(hostId))
        if (memberToKick.status == LobbyMemberStatus.IN_GAME) {
            throw UserInGameAttemptKickException(hostId)
        }
        blackList.add(kickableUserId)
        members.remove(memberToKick)
    }

    fun isHost(member: LobbyMember): Boolean {
        return member.userId == (host?.userId ?: false)
    }
}