package ru.rsreu.jackal.exception

import ru.rsreu.jackal.api.lobby.Lobby

class UsersNotHaveActiveConnectionException(val lobby: Lobby, val notConnectedUsersIds: List<Long>) : RuntimeException()