package ru.rsreu.jackal.models

data class Lobby(
    val id: Long,
    val title: String,
    var password: String? = null,
    var host: LobbyMemberInfo,
    var gameId: Long? = null,
    var isInGame: Boolean = false,
) {
    private val members: MutableCollection<LobbyMemberInfo> = mutableListOf()

    private val blackList: MutableList<Long> = mutableListOf()

    init {
        members.add(host)
    }

    fun addToBlackList(id: Long) = blackList.add(id)

    fun checkUserInBlackListById(id: Long) = blackList.contains(id)

    fun checkUserInLobbyById(userId: Long) = members.find { it.userId == userId } != null

    fun addUser(userId: Long) = members.add(LobbyMemberInfo(userId))
}
