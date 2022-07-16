package ru.rsreu.jackal.models

import ru.rsreu.jackal.shared_models.LobbyMemberStatus

data class LobbyMember(
    val userId: Long,
    var status: LobbyMemberStatus = LobbyMemberStatus.NOT_CONNECTED
)