package ru.rsreu.jackal.websocket.kicking.dto

import ru.rsreu.jackal.websocket.WebSocketResponseType

data class UserKickedInfoResponse(val type: WebSocketResponseType = WebSocketResponseType.KICKING_ERROR)