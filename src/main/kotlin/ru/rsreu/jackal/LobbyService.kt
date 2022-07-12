package ru.rsreu.jackal

import org.springframework.stereotype.Service
import ru.rsreu.jackal.models.Lobby

@Service
class LobbyService(private val repository: LobbyRepository) {
    fun test(): Collection<Lobby> {
        repository.createLobby()
        return repository.getAll()
    }
}
