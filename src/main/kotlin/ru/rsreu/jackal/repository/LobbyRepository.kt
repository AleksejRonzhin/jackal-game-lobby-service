package ru.rsreu.jackal.repository

import org.springframework.stereotype.Repository
import ru.rsreu.jackal.models.Lobby
import ru.rsreu.jackal.models.LobbyMemberInfo

@Repository
class LobbyRepository {
    private val lobbies: MutableCollection<Lobby> = mutableListOf()

    companion object {
        var lastId = 1L
    }

    fun createLobby(name: String, password: String?, hostId: Long): Long {
        lobbies.add(Lobby(id = ++lastId, password = password, host = LobbyMemberInfo(hostId)))
        return lastId
    }

    fun findByUser(userId: Long): Lobby? =
        lobbies.find { lobby ->
            lobby.members.find { it.userId == userId } != null
        }
}