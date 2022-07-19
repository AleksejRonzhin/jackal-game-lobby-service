package ru.rsreu.jackal.api.lobby.service.connection_checking

import ru.rsreu.jackal.api.lobby.LobbyConnectionCheckingEntity

class UserConnectionCheckTask(private val entity: LobbyConnectionCheckingEntity) : Runnable {
    var notConnectedUsersIds: Set<Long>? = null
    override fun run() {
        notConnectedUsersIds = entity.membersStatuses.filterValues { !it }.keys
    }
}