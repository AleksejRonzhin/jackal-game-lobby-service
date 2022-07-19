package ru.rsreu.jackal.api.lobby

import java.util.concurrent.ConcurrentHashMap

class LobbyConnectionCheckingEntity(lobby: Lobby) {
    val lobbyId = lobby.id
    val membersStatuses: ConcurrentHashMap<Long, Boolean> = ConcurrentHashMap()

    init {
        lobby.getAllMembers().associateTo(membersStatuses) { it.userId to false }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as LobbyConnectionCheckingEntity

        if (lobbyId != other.lobbyId) return false

        return true
    }

    override fun hashCode(): Int {
        return lobbyId.hashCode()
    }


}