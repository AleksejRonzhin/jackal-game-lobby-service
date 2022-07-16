package ru.rsreu.jackal.exception

class NotHostKickAttemptException(userId: Long) : WebSocketException(userId)