package ru.rsreu.jackal.models

data class LobbyMemberInfo(
    val userId: Long,
    val status: LobbyMemberStatus = LobbyMemberStatus.NOT_CONNECTED
)

enum class LobbyMemberStatus{
    NOT_CONNECTED, READY, NOT_READY
}
