package ru.rsreu.jackal.models

data class Lobby(
    val id: Long,
    val members: MutableCollection<LobbyMemberInfo>
)
