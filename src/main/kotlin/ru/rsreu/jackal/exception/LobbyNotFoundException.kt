package ru.rsreu.jackal.exception

class LobbyNotFoundException() : WebSocketException() {
    var wsSendingUserId: Long = -1L

    constructor(wsSendingUserId: Long) : this() {
        this.wsSendingUserId = wsSendingUserId
    }
}