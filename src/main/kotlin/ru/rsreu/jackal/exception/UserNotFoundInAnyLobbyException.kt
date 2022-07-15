package ru.rsreu.jackal.exception

class UserNotFoundInAnyLobbyException(userId: Long) : WebSocketException(userId)