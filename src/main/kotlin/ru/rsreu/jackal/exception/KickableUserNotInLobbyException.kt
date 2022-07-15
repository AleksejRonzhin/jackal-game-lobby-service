package ru.rsreu.jackal.exception

class KickableUserNotInLobby(userId: Long) : WebSocketException(userId)