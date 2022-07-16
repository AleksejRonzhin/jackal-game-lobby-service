package ru.rsreu.jackal.exception

class UserInGameAttemptKickException(userId: Long) : WebSocketException(userId)