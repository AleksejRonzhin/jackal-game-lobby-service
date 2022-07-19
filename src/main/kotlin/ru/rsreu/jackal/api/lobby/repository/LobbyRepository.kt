package ru.rsreu.jackal.api.lobby.repository

import org.springframework.stereotype.Repository
import ru.rsreu.jackal.api.lobby.Lobby
import ru.rsreu.jackal.shared_models.LobbyMemberInfo
import java.util.*

@Repository
class LobbyRepository {
    private val lobbies: MutableCollection<Lobby> = mutableListOf()

    fun createLobby(title: String, password: String?, hostId: Long): Lobby {
        val lobby = Lobby(id = UUID.randomUUID(), title = title, password = password, host = LobbyMemberInfo(hostId))
        lobbies.add(lobby)
        return lobby
    }

    fun findLobbyByUser(userId: Long): Lobby? = lobbies.find { lobby ->
        lobby.checkUserInLobbyById(userId)
    }

    fun findLobbyByTitle(title: String): Lobby? = lobbies.find { it.title == title }

    fun findLobbyById(id: UUID): Lobby? = lobbies.find { it.id == id }

    fun removeLobbyById(id: UUID) = lobbies.removeIf { it.id == id }

    fun getAllLobbies(): Collection<Lobby> = lobbies
}