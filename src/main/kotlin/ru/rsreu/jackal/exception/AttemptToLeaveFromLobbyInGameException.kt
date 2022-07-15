package ru.rsreu.jackal.exception

class AttemptToLeaveFromLobbyInGameException(userId: Long) : WebSocketException(userId)