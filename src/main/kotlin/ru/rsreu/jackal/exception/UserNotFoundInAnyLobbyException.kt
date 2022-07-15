package ru.rsreu.jackal.exception

class UserNotFoundInAnyLobbyException(val userId: Long) : RuntimeException()