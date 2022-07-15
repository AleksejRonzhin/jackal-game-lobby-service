package ru.rsreu.jackal.exception

class AttemptToChangeStateInGameException(userId: Long) : WebSocketException(userId)