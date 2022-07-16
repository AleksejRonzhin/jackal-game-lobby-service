package ru.rsreu.jackal.exception

class KickableUserNotInLobbyException(userId: Long) : WebSocketException(userId)