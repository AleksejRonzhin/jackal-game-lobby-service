package ru.rsreu.jackal.exception

class LobbyNotFoundException : WebSocketException {
    constructor() : super()
    constructor(wsSendingUserId: Long) : super(wsSendingUserId)
}