package ru.rsreu.jackal.exception

class AttemptToChangeStateNotConnectedException(userId: Long) : WebSocketException(userId)