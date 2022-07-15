package ru.rsreu.jackal.exception

class LobbyNotFoundForUserConnectionInfoException(userId: Long) : WebSocketException(userId)