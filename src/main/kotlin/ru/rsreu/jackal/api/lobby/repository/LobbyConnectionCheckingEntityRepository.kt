package ru.rsreu.jackal.api.lobby.repository

import org.springframework.stereotype.Component
import ru.rsreu.jackal.api.lobby.LobbyConnectionCheckingEntity
import ru.rsreu.jackal.exception.LobbyAlreadyGettingReadyException
import java.util.*

@Component
class LobbyConnectionCheckingEntityRepository {
    private val connectionChecks: MutableList<LobbyConnectionCheckingEntity> = mutableListOf()

    fun addCheck(checkingEntity: LobbyConnectionCheckingEntity) {
        if (connectionChecks.contains(checkingEntity)) {
            throw LobbyAlreadyGettingReadyException()
        }
        connectionChecks.add(checkingEntity)
    }

    fun getByLobbyId(lobbyId: UUID) = connectionChecks.find { it.lobbyId == lobbyId }

    fun removeByLobbyId(lobbyId: UUID) = connectionChecks.removeIf { it.lobbyId == lobbyId }
}