package ru.rsreu.jackal.api.lobby

import ru.rsreu.jackal.exception.*
import ru.rsreu.jackal.shared_models.LobbyMemberInfo
import ru.rsreu.jackal.shared_models.LobbyMemberStatus
import java.util.*

class Lobby(
    val id: UUID,
    val title: String,
    var password: String? = null,
    host: LobbyMemberInfo,
    var gameModeId: Long? = null,
    var isInGame: Boolean = false,
) {
    private val members: MutableCollection<LobbyMemberInfo> = mutableListOf()

    private val blackList: MutableList<Long> = mutableListOf()

    var host: LobbyMemberInfo? = host
        private set

    init {
        members.add(host)
    }

    fun checkUserInBlackListById(id: Long) = blackList.contains(id)

    fun checkUserInLobbyById(userId: Long) = members.find { it.userId == userId } != null

    fun addUser(userId: Long) = members.add(LobbyMemberInfo(userId))

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

    fun changeMemberStateAndGetInfo(userId: Long): LobbyMemberInfo {
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

    fun setAllMembersInGame() {
        members.forEach {
            it.status = LobbyMemberStatus.IN_GAME
        }
    }

    fun setAllMembersNotReady() {
        members.forEach {
            it.status = LobbyMemberStatus.NOT_READY
        }
    }
}