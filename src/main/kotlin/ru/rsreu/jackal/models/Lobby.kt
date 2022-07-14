package ru.rsreu.jackal.models

data class Lobby(
    val id: Long,
    var password: String? = null,
    var host: LobbyMemberInfo
) {
    val members: MutableCollection<LobbyMemberInfo> = mutableListOf()
    init {
        members.add(host)
    }
}
