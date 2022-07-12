package ru.rsreu.jackal.models

data class LobbyMemberInfo(
    val userId: Long,
    val status: LobbyMemberStatus
)

enum class LobbyMemberStatus{
    NOT_CONNECTED, READY, NOT_READY
}
