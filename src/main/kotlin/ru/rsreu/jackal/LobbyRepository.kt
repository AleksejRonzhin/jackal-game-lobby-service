package ru.rsreu.jackal

import org.springframework.stereotype.Repository
import ru.rsreu.jackal.models.Lobby
import ru.rsreu.jackal.models.WebSocketInfo

@Repository
class LobbyRepository {
    private val lobbies: MutableCollection<Lobby> = mutableListOf()

    fun getAll(): Collection<Lobby> = lobbies

    fun createLobby(){
        lobbies.add(Lobby(1, mutableListOf(), WebSocketInfo()))
    }
}