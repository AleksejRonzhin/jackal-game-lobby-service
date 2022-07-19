package ru.rsreu.jackal.api.lobby.websocket.kicking.dto

import ru.rsreu.jackal.api.lobby.websocket.WebSocketMessage
import ru.rsreu.jackal.api.lobby.websocket.WebSocketMessageType

class UserKickedInfoForOneResponse : WebSocketMessage(WebSocketMessageType.KICKED_INFO_FOR_ONE)