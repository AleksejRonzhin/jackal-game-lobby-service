package ru.rsreu.jackal.exception

class InvalidForLobbyTokenException(userId: Long) : WebSocketException(userId)