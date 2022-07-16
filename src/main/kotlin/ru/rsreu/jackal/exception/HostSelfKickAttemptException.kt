package ru.rsreu.jackal.exception

class HostSelfKickAttemptException(userId: Long) : WebSocketException(userId)