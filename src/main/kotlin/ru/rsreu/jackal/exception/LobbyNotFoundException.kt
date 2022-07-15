package ru.rsreu.jackal.exception

class LobbyNotFoundException() : RuntimeException() {
    var wsSendingUserId: Long = -1L

    constructor(sendingUserId: Long) : this() {
        this.wsSendingUserId = sendingUserId
    }
}