package ru.rsreu.jackal

import org.springframework.stereotype.Repository
import ru.rsreu.jackal.models.Lobby

@Repository
class LobbyRepository {
    private val lobbies: MutableCollection<Lobby> = mutableListOf()

    companion object {
        var lastId = 1L
    }

    fun getAll(): Collection<Lobby> = lobbies

    fun createLobby(): Long {
        val lobbyId = lastId++
        lobbies.add(Lobby(lobbyId, mutableListOf()))
        return lobbyId
    }
}