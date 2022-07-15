package ru.rsreu.jackal.exception

abstract class WebSocketException() : RuntimeException() {
    var userId: Long = -1L

    constructor(userId: Long) : this() {
        this.userId = userId
    }
}